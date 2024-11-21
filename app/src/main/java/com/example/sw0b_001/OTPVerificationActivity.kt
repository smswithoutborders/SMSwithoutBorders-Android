package com.example.sw0b_001

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import io.grpc.StatusRuntimeException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.CountDownTimer
import com.example.sw0b_001.Models.Publishers
import com.example.sw0b_001.Models.Vaults
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import vault.v1.Vault
import java.util.concurrent.Flow.Publisher


class OTPVerificationActivity : AppCompactActivityCustomized() {

    enum class Type(val type: String) {
        CREATE("CREATE"),
        AUTHENTICATE("AUTHENTICATE"),
        RECOVER("RECOVER"),
        PNBA("PNBA")
    }

    private val SMS_CONSENT_REQUEST = 2  // Set to an unused request code
    private lateinit var phoneNumber: String
    private var password : String? = null
    private var platform : String? = null
    private var countryCode : String? = null
    private var nextAttemptTimestamp : String? = null

    private lateinit var twoFaPasswordLayout: TextInputLayout
    private lateinit var twoFaPasswordInput: TextInputEditText
    private lateinit var codeInput: TextInputEditText

    private lateinit var vault: Vaults
    private lateinit var type: Type

    private var isTwoStepVerificationEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_otp_verification_code)

        phoneNumber = intent.getStringExtra("phone_number")!!
        platform = intent.getStringExtra("platform")
        password = intent.getStringExtra("password")
        countryCode = intent.getStringExtra("country_code")
        nextAttemptTimestamp = intent.getStringExtra("next_attempt_timestamp")

        twoFaPasswordLayout = findViewById(R.id.password_layout)
        twoFaPasswordInput = findViewById(R.id.two_fa_password_input)
        codeInput = findViewById(R.id.ownership_verification_input)

        val resendCodeTextView = findViewById<MaterialTextView>(R.id.ownership_resend_code_by_sms_btn)
        val telegramInfoBox = findViewById<MaterialCardView>(R.id.telegram_info_box)
        if (intent.hasExtra("platform")) {
            telegramInfoBox.visibility = View.VISIBLE
            resendCodeTextView.visibility = View.GONE
        } else {
            telegramInfoBox.visibility = View.GONE
            nextAttemptTimestamp?.let { timestampString ->
                val timestamp = timestampString.toLongOrNull() ?: 0
                startCountdownTimer(timestamp,
                    onTick = { secondsRemaining ->
                        runOnUiThread {
                            resendCodeTextView.text = getString(R.string.ownership_resend_code_by_sms, secondsRemaining) // Show countdown string
                            resendCodeTextView.setOnClickListener(null)
                            resendCodeTextView.isClickable = false
                            resendCodeTextView.visibility = View.VISIBLE
                        }
                    },
                    onFinish = {
                        runOnUiThread {
                            resendCodeTextView.text = getString(R.string.resend_code)
                            resendCodeTextView.isClickable = true
                            resendCodeTextView.setOnClickListener {
                                finish()
                            }
                        }
                    })
            }
        }

        intent.getStringExtra("type")?.let {
            type = Type.valueOf(it)
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(applicationContext, smsVerificationReceiver, intentFilter,
                ContextCompat.RECEIVER_EXPORTED)
        configureVerificationListener()

        if(BuildConfig.DEBUG) {
           codeInput.text = Editable.Factory().newEditable("123456")
        }

        findViewById<MaterialButton>(R.id.ownership_verification_btn).setOnClickListener {
            if(codeInput.text.isNullOrEmpty()) {
                codeInput.error = getString(R.string.owernship_otp_please_enter_a_valid_code)
                return@setOnClickListener
            }
            it.isEnabled = false
            submitOTPCode(it, codeInput.text.toString())
        }

        findViewById<MaterialTextView>(R.id.ownership_resend_code_by_sms_btn)
                .setOnClickListener {
                    finish()
                }

        vault = Vaults(applicationContext)
    }

    private fun startCountdownTimer(nextAttemptTimestamp: Long, onTick: (Long) -> Unit, onFinish: () -> Unit) {
        val currentTimeMillis = System.currentTimeMillis()
        val timeDiffInMillis = nextAttemptTimestamp * 1000 - currentTimeMillis

        if (timeDiffInMillis > 0) {
            object : CountDownTimer(timeDiffInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    onTick(secondsRemaining)
                }

                override fun onFinish() {
                    onFinish()
                }
            }.start()
        } else {
            onFinish()
        }
    }

    private val smsVerificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get consent intent
                        val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            if (consentIntent != null) {
                                startActivityForResult(consentIntent, SMS_CONSENT_REQUEST)
                            }
                        } catch (e: ActivityNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        // Time out occurred, handle the error.
                    }
                }
            }
        }
    }

    private fun configureVerificationListener() {
        // Start listening for SMS User Consent broadcasts from senderPhoneNumber
        // The Task<Void> will be successful if SmsRetriever was able to start
        // SMS User Consent, and will error if there was an error starting.
        val smsSenderNumber = "VERIFY"
        val task = SmsRetriever.getClient(applicationContext).startSmsUserConsent(smsSenderNumber)
        task.addOnSuccessListener {
            Log.d(javaClass.name, "Successfully showed user consent screen")
        }

        task.addOnFailureListener {
            Log.e(javaClass.name, "Exception showing user consent screen", it)
        }
    }

    private fun submitOTPCode(submitBtnView: View, code: String) {
        val linearProgressIndicator = findViewById<LinearProgressIndicator>(R.id.ownership_progress_bar)
        linearProgressIndicator.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Default).launch {
            try {
                when(type) {
                    Type.CREATE -> {
                        password?.let {
                            vault.createEntity(applicationContext, phoneNumber, countryCode!!,
                                password!!, code)
                        }
                    }
                    Type.AUTHENTICATE -> {
                        password?.let {
                            vault.authenticateEntity(applicationContext, phoneNumber, password!!,
                                code)
                        }
                    }
                    Type.RECOVER -> {
                        password?.let {
                            vault.recoverEntityPassword(applicationContext, phoneNumber, password!!,
                                code)
                        }
                    }
                    Type.PNBA -> {
                        platform?.let {
                            val llt = Vaults.fetchLongLivedToken(applicationContext)
                            val publisher = Publishers(applicationContext)
                            val r = publisher.phoneNumberBaseAuthenticationExchange(code,
                                llt, phoneNumber, platform!!)

                            if (r.twoStepVerificationEnabled) {
                                isTwoStepVerificationEnabled = true
                                runOnUiThread {
                                    Toast.makeText(applicationContext, getString(R.string.two_factor_auth_enabled), Toast.LENGTH_SHORT).show()
                                    twoFaPasswordLayout.visibility = View.VISIBLE
                                    codeInput.isEnabled = false
                                    findViewById<MaterialButton>(R.id.ownership_verification_btn).setOnClickListener {
                                        submitPassword(it)
                                    }
                                }
                            } else {
                                vault.refreshStoredTokens(applicationContext)
                                runOnUiThread {
                                    setResult(Activity.RESULT_OK)
                                    finish()
                                }
                            }
                        }
                    }
                }

                vault.refreshStoredTokens(applicationContext)
                runOnUiThread {
                    setResult(Activity.RESULT_OK)
                    if (platform != "telegram") {
                        finish()
                    }
                }
            } catch(e: StatusRuntimeException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, e.status.description, Toast.LENGTH_SHORT)
                        .show()
                }
            } catch(e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                }
            } finally {
                runOnUiThread {
                    linearProgressIndicator.visibility = View.GONE
                    submitBtnView.isEnabled = true
                }
            }
        }
    }

    private fun submitPassword(submitBtnView: View) {
        val password = twoFaPasswordInput.text.toString()
        val code = codeInput.text.toString()

        val linearProgressIndicator = findViewById<LinearProgressIndicator>(R.id.ownership_progress_bar)
        linearProgressIndicator.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Default).launch {
            try {
                platform?.let {
                    val llt = Vaults.fetchLongLivedToken(applicationContext)
                    val publisher = Publishers(applicationContext)
                    val r = publisher.phoneNumberBaseAuthenticationExchange(code, llt, phoneNumber, platform!!, password)
                    if (r.success) {
                        vault.refreshStoredTokens(applicationContext)
                        runOnUiThread {
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    } else {
                        runOnUiThread {
                            AlertDialog.Builder(this@OTPVerificationActivity)
                                .setTitle(getString(R.string.error_title))
                                .setMessage(getString(R.string.incorrect_password_message))
                                .setPositiveButton(getString(R.string.ok_button_text), null)
                                .show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    AlertDialog.Builder(this@OTPVerificationActivity)
                        .setTitle(getString(R.string.error_title))
                        .setMessage(getString(R.string.telegram_auth_error))
                        .setPositiveButton(getString(R.string.ok_button_text), null)
                        .show()
//                    finish()
                }
            } finally {
                runOnUiThread {
                    linearProgressIndicator.visibility = View.GONE
                    submitBtnView.isEnabled = true
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            SMS_CONSENT_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    val smsTemplate = getString(R.string.otp_verification_code_template);

                    val code = message?.split(smsTemplate.toRegex())
                    if(code != null && code.size > 1)
                        findViewById<TextInputEditText>(R.id.ownership_verification_input)
                                ?.setText(code[1].replace(" ".toRegex(), ""))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vault.shutdown()
    }
}