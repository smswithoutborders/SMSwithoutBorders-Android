package com.example.sw0b_001.Modals


import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.sw0b_001.R

class BridgesAuthRequestModalFragment : BottomSheetDialogFragment(R.layout.fragment_modal_bridges_auth) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val okayButton = view.findViewById<Button>(R.id.okay_button)

        cancelButton.setOnClickListener {
            dismiss()
        }

        okayButton.setOnClickListener {
            dismiss()
        }
    }
}