package com.example.sw0b_001.Modals

package com.example.sw0b_001.Bridges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.sw0b_001.R

class BridgesAuthRequestModalFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modal_bridges_auth, container, false)
    }

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