package com.example.sw0b_001

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.sw0b_001.Bridges.BridgesWelcomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BridgesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bridges)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<BridgesWelcomeFragment>(R.id.bridges_fragment_container)
            }
        }

//        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bridges_bottom_navbar)
//        bottomNavBar.selectedItemId = R.id.bridges_navbar
//        bottomNavBar.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.recents_navbar -> {
//                    bottomNavBar.selectedItemId = R.id.recents_navbar
//                    finish()
//                    true
//                }
//
//                R.id.gateway_clients_navbar -> {
//                    bottomNavBar.selectedItemId = R.id.gateway_clients_navbar
//                    finish()
//                    true
//                }
//
//                R.id.bridges_navbar -> {
//                    bottomNavBar.selectedItemId = R.id.bridges_navbar
//                    supportFragmentManager.commit {
//                        replace<BridgesWelcomeFragment>(R.id.bridges_fragment_container)
//                    }
//                    true
//                }
//
//                else -> false
//            }
//        }
    }
}