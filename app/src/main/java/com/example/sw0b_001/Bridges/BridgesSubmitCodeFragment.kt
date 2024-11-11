package com.example.sw0b_001.Bridges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.sw0b_001.R
import com.google.android.material.card.MaterialCardView

class BridgesSubmitCodeFragment() : Fragment(R.layout.fragment_bridges_auth_code) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoBoxTextView = view.findViewById<TextView>(R.id.telegram_info_text_view)
        infoBoxTextView.text = getString(R.string.bridges_submit_code_info)

        val infoBoxCardView = view.findViewById<MaterialCardView>(R.id.telegram_info_box)
        val infoBoxColor = ContextCompat.getColor(requireContext(), R.color.md_theme_primaryContainer)
        infoBoxCardView.setCardBackgroundColor(infoBoxColor)
    }
}