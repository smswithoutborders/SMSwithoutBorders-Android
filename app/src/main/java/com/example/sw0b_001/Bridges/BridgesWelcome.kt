package com.example.sw0b_001.Bridges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.sw0b_001.R

class BridgesWelcomeFragment : Fragment(R.layout.fragment_bridges_welcome) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get references to the buttons
        val authenticateButton = view.findViewById<Button>(R.id.authenticate_button)
        val submitCodeButton = view.findViewById<Button>(R.id.submit_code_button)

        // Set click listeners for the buttons
        authenticateButton.setOnClickListener {
            // Handle authentication logic here
        }

        submitCodeButton.setOnClickListener {
            // Handle code submission logic here
        }

    }
}