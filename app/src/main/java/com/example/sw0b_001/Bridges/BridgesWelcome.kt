package com.example.sw0b_001.Bridges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.sw0b_001.Modals.BridgesAuthRequestModalFragment
import com.example.sw0b_001.R


class BridgesWelcomeFragment : Fragment(R.layout.fragment_bridges_welcome) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authenticateButton = view.findViewById<Button>(R.id.authenticate_button)
        val submitCodeButton = view.findViewById<Button>(R.id.submit_code_button)

        authenticateButton.setOnClickListener {
            val modalBottomSheet = BridgesAuthRequestModalFragment()
            modalBottomSheet.show(parentFragmentManager, modalBottomSheet.tag)
        }

        submitCodeButton.setOnClickListener {
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()

            transaction.replace(R.id.bridges_fragment_container, BridgesSubmitCodeFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}