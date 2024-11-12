package com.example.sw0b_001.Bridges

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sw0b_001.Models.Bridges
import com.example.sw0b_001.Models.Publishers
import com.example.sw0b_001.R
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class BridgesSubmitCodeActivity() : AppCompatActivity() {
    private val SMS_CONSENT_REQUEST = 2  // Set to an unused request code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bridges_auth_code)

        val myToolbar = findViewById<MaterialToolbar>(R.id.bridge_auth_code_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        ContextCompat.registerReceiver(
            applicationContext, smsVerificationReceiver, intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
        configureVerificationListener()

        findViewById<MaterialButton>(R.id.bridge_auth_code_verify_and_save_btn).setOnClickListener {
            val responsePayload = findViewById<TextInputEditText>(R.id.bridge_sms_code_input).text
                .toString()
            val values = Bridges.parseAuthResponse(responsePayload)

            values.first?.let { authCode ->
                values.second?.let { publicKey ->
                    Bridges.saveAuthCode(applicationContext, authCode)
                    Publishers.storeArtifacts(applicationContext,
                        Base64.encodeToString(publicKey, Base64.DEFAULT))

                    assert(Bridges.canPublish(applicationContext))

                    Toast.makeText(applicationContext,
                        getString(R.string.aliases_configured_successfully),
                        Toast.LENGTH_LONG).show()

                    finish()
                }
            }
        }
    }

    private fun configureVerificationListener() {
        // TODO: scan multiple phone numbers to see which one sends the code
        // Start listening for SMS User Consent broadcasts from senderPhoneNumber
        // The Task<Void> will be successful if SmsRetriever was able to start
        // SMS User Consent, and will error if there was an error starting.
        val smsSenderNumber = getString(R.string.default_relaysms_twilio)
        val task = SmsRetriever.getClient(applicationContext).startSmsUserConsent(smsSenderNumber)
        task.addOnSuccessListener {
            Log.d(javaClass.name, "Successfully showed user consent screen")
        }

        task.addOnFailureListener {
            Log.e(javaClass.name, "Exception showing user consent screen", it)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            SMS_CONSENT_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    findViewById<TextInputEditText>(R.id.bridge_sms_code_input).setText(message)
                    findViewById<MaterialButton>(R.id.bridge_auth_code_verify_and_save_btn).apply {
                        this.isEnabled = false
                        performClick()
                    }
                }
            }
        }
    }
}