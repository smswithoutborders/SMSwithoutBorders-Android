package com.example.sw0b_001

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sw0b_001.Data.ThreadExecutorPool
import com.example.sw0b_001.Data.UserArtifactsHandler
import com.example.sw0b_001.Data.v2.Vault_V2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.serialization.json.Json

class LoginModalFragment(val successRunnable: Runnable) : BottomSheetDialogFragment() {

    public lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_modal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.login_btn).setOnClickListener {
            login(view)
        }

        val customUrlView = view.findViewById<TextInputLayout>(R.id.login_url)
        view.findViewById<MaterialTextView>(R.id.login_advanced_toggle).setOnClickListener {
            if(customUrlView.visibility == View.VISIBLE)
                customUrlView.visibility = View.INVISIBLE
            else
                customUrlView.visibility = View.VISIBLE
        }

        val bottomSheet = view.findViewById<View>(R.id.login_constraint)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isDraggable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun login(view: View) {
        val phonenumber = view.findViewById<TextInputEditText>(R.id.login_phonenumber_text_input)
                .text.toString()

        val password = view.findViewById<TextInputEditText>(R.id.login_password_text_input)
                .text.toString()

        val url = view.context.getString(R.string.smswithoutborders_official_site_login)
        ThreadExecutorPool.executorService.execute {
            activity?.runOnUiThread {
                view.findViewById<LinearProgressIndicator>(R.id.login_progress_bar)
                        .visibility = View.VISIBLE
            }

            try {
                val networkResponseResults = Vault_V2.login(phonenumber, password, url)
                val uid = Json.decodeFromString<Vault_V2.UID>(networkResponseResults.result.get()).uid

                UserArtifactsHandler.storeUID(view.context, uid)
                dismiss()

                successRunnable.run()
            } catch(e: Exception) {
                when(e.message) {
                    Vault_V2.INVALID_CREDENTIALS_EXCEPTION -> {
                        TODO("Implement inform user invalid credentials")
                    }
                    Vault_V2.SERVER_ERROR_EXCEPTION -> {
                        TODO("Implement inform user server error")
                    }
                }
            } finally {
                activity?.runOnUiThread {
                    view.findViewById<LinearProgressIndicator>(R.id.login_progress_bar)
                            .visibility = View.GONE
                }
            }
        }
    }

//    private fun storePlatforms(user: BackendCommunications, url: String, headers: Headers) {
//        val (_, response, result) = user.getPlatforms(url, headers)
//        val platforms = Json.decodeFromString<BackendCommunications.Platforms>(result.get())
//
//        Datastore.getDatastore(applicationContext)
//                .platformDao().deleteAll()
//
//        platforms.saved_platforms.forEach {
//            val platform = Platforms()
//            platform.name = it.name
//            platform.description = ""
//            platform.type = it.type
//            platform.letter = it.letter
//            platform.logo = PlatformsHandler
//                    .hardGetLogoByName(applicationContext, it.name)
//            Datastore.getDatastore(applicationContext)
//                    .platformDao().insert(platform)
//        }
//    }

//    private fun login(view: View) {
//        val cardView = findViewById<MaterialCardView>(R.id.login_status_card)
//        cardView.visibility = View.GONE
//
//        val phoneNumber = findViewById<TextInputEditText>(R.id.login_phonenumber_text_input)
//                .text.toString()
//        val password = findViewById<TextInputEditText>(R.id.login_password_text_input)
//                .text.toString()
//        val customUrl = findViewById<TextInputEditText>(R.id.login_url_input)
//
//        var url = getString(R.string.default_login_url)
//        if(customUrl != null && customUrl.text != null)
//            url = customUrl.text.toString()
//
//        val progressIndicator = findViewById<LinearProgressIndicator>(R.id.login_progress_bar)
//        progressIndicator.visibility = View.VISIBLE
//
//        ThreadExecutorPool.executorService.execute(Runnable {
//            val networkResponseResults = BackendCommunications.login(phoneNumber, password, url)
//            when(networkResponseResults.result) {
//                is Result.Success -> {
//                    val obj = Json
//                            .decodeFromString<BackendCommunications.UID>(
//                                    networkResponseResults.result.get())
//                    val uid = obj.uid
//                    BackendCommunications(obj.uid).storeUID(applicationContext, url)
//                    storePlatforms(BackendCommunications(uid),
//                            getString(R.string.default_backend_url),
//                            networkResponseResults.response!!.headers)
//                    startActivity(Intent(this, HomepageActivity::class.java))
//                }
//                is Result.Failure -> {
//                    val errorTextView = findViewById<MaterialTextView>(R.id.login_error_text)
//                    runOnUiThread(Runnable {
//                        cardView.visibility = View.VISIBLE
//                        errorTextView.visibility = View.VISIBLE
//                        errorTextView.text = getString(R.string.login_wrong_credentials)
//                    })
//                }
//                else -> {
//                    runOnUiThread(Runnable {
//                        cardView.visibility = View.VISIBLE
//                        findViewById<MaterialButton>(R.id.login_retry_btn).visibility = View.VISIBLE
//                    })
//                }
//            }
//            runOnUiThread(Runnable {
//                progressIndicator.visibility = View.GONE
//            })
//        })
//    }
//
}