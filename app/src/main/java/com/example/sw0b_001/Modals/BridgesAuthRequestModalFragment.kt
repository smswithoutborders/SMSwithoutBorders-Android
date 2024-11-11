package com.example.sw0b_001.Modals


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.sw0b_001.Bridges.BridgesSubmitCodeFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.sw0b_001.R
import com.google.android.material.bottomsheet.BottomSheetBehavior


class BridgesAuthRequestModalFragment(var verifyNowRunnable: Runnable) : BottomSheetDialogFragment(R.layout.fragment_modal_bridges_auth) {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var verifyNowLink: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet = view.findViewById<View>(R.id.bridges_constraint)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isDraggable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val okayButton = view.findViewById<Button>(R.id.okay_button)

        cancelButton.setOnClickListener {
            dismiss()
        }

        okayButton.setOnClickListener {
            dismiss()
        }

        verifyNowLink = view.findViewById(R.id.bridges_verify_now_link)
        verifyNowLink.setOnClickListener {
            dismiss()
            verifyNowRunnable.run()
        }
    }
}