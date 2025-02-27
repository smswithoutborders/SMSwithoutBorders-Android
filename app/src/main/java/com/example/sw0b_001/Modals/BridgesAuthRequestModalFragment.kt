package com.example.sw0b_001.Modals


import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.sw0b_001.Models.Bridges
import com.example.sw0b_001.Models.GatewayClients.GatewayClientsCommunications
import com.example.sw0b_001.Models.SMSHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.sw0b_001.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton


class BridgesAuthRequestModalFragment(
    var canPublish: Boolean = false,
    var completeCallback: Runnable,
) : BottomSheetDialogFragment(
    if(canPublish) R.layout.fragment_modal_bridge_can_publish
    else R.layout.fragment_modal_bridges_auth
) {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var verifyNowLink: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet = view.findViewById<View>(R.id.bridges_constraint)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isDraggable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        if(canPublish) {
            view.findViewById<MaterialButton>(R.id.bridge_compose_new_btn).setOnClickListener {
                completeCallback.run()
                dismiss()
            }
        }
        else {
            view.findViewById<MaterialButton>(R.id.bridge_auth_request_authentication_btn)
                .setOnClickListener {
                    var authRequest = Base64.encodeToString(Bridges.authRequest(requireContext()),
                        Base64.DEFAULT)
                    val gatewayClientMsisdn = GatewayClientsCommunications(requireContext())
                        .getDefaultGatewayClient()
                    if(gatewayClientMsisdn.isNullOrBlank()) {
                        Toast.makeText(requireContext(),
                            getString(R.string.please_set_a_default_gateway_client),
                            Toast.LENGTH_LONG).show()
                    } else {
                        completeCallback.run()
                        startActivity(SMSHandler.transferToDefaultSMSApp(
                            requireContext(),
                            gatewayClientMsisdn,
                            authRequest
                        ))
                    }
                    dismiss()
                }

            verifyNowLink = view.findViewById(R.id.bridges_verify_now_link)
            verifyNowLink.setOnClickListener {
                completeCallback.run()
                dismiss()
            }
        }
    }
}