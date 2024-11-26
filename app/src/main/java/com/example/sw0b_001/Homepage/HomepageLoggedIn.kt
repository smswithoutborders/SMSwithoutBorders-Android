package com.example.sw0b_001.Homepage

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sw0b_001.Bridges.BridgesSubmitCodeActivity
import com.example.sw0b_001.Database.Datastore
import com.example.sw0b_001.EmailViewActivity
import com.example.sw0b_001.MessageViewActivity
import com.example.sw0b_001.Modals.AvailablePlatformsModalFragment
import com.example.sw0b_001.Modals.BridgesAuthRequestModalFragment
import com.example.sw0b_001.Modals.LoginModalFragment
import com.example.sw0b_001.Modals.PlatformComposers.EmailComposeModalFragment
import com.example.sw0b_001.Models.Bridges
import com.example.sw0b_001.Models.GatewayClients.GatewayClient
import com.example.sw0b_001.Models.Messages.MessagesRecyclerAdapter
import com.example.sw0b_001.Models.Messages.MessagesViewModel
import com.example.sw0b_001.Models.Platforms.Platforms
import com.example.sw0b_001.Models.Vaults
import com.example.sw0b_001.R
import com.example.sw0b_001.TextViewActivity
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.text.isNotBlank

class HomepageLoggedIn : Fragment(R.layout.fragment_homepage_logged_in) {

    private lateinit var messagesRecyclerView : RecyclerView
    private lateinit var recentRecyclerAdapter: MessagesRecyclerAdapter

    private val loginSuccessRunnable = Runnable {
        if (isAdded) {
            activity?.supportFragmentManager?.commit {
                replace(R.id.homepage_fragment_container, HomepageLoggedIn(), "homepage_fragment")
            }
        } else {
            Log.d("HomepageLoggedIn", "Fragment not attached")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        GatewayClient.refreshGatewayClients(requireContext()) { }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesRecyclerView = view.findViewById(R.id.recents_recycler_view)

        configureRecyclerHandlers(view)

        view.findViewById<MaterialButton>(R.id.homepage_compose_new_btn)
            .setOnClickListener { v ->
                if (Vaults.fetchLongLivedToken(requireContext()).isNotBlank()) {
                    showPlatformsModal(AvailablePlatformsModalFragment.Type.SAVED)
                } else {
                    showLoginModal()
                }
            }

        view.findViewById<MaterialButton>(R.id.homepage_add_new_btn)
            .setOnClickListener { v ->
                if (Vaults.fetchLongLivedToken(requireContext()).isNotBlank()) {
                    showPlatformsModal(AvailablePlatformsModalFragment.Type.AVAILABLE)
                } else {
                    showLoginModal()
                }
            }

    }

    private fun configureRecyclerHandlers(view: View) {
        val linearLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false);
        val noRecentMessagesText = view.findViewById<TextView>(R.id.no_recent_messages)
        messagesRecyclerView.layoutManager = linearLayoutManager

        val viewModel: MessagesViewModel by viewModels()

        CoroutineScope(Dispatchers.Default).launch {
            val availablePlatforms = Datastore.getDatastore(requireContext()).availablePlatformsDao()
                .fetchAllList();
            recentRecyclerAdapter = MessagesRecyclerAdapter(availablePlatforms)

            activity?.runOnUiThread {
                messagesRecyclerView.adapter = recentRecyclerAdapter
                viewModel.getMessages(requireContext()).observe(viewLifecycleOwner) {
                    recentRecyclerAdapter.mDiffer.submitList(it) {
                        messagesRecyclerView.smoothScrollToPosition(0)
                    }
                    if (it.isNullOrEmpty()) {
                        noRecentMessagesText.visibility = View.VISIBLE
                        view.findViewById<View>(R.id.homepage_no_message_image).visibility = View.VISIBLE

                        view.findViewById<View>(R.id.homepage_compose_new_btn).visibility = View.VISIBLE
                        view.findViewById<View>(R.id.homepage_add_new_btn).visibility = View.VISIBLE

                        view.findViewById<View>(R.id.homepage_compose_new_btn1).visibility = View.GONE
                        view.findViewById<View>(R.id.homepage_add_new_btn1).visibility = View.GONE

                        view.findViewById<MaterialButton>(R.id.homepage_bridges_auth_btn).apply {
                            visibility = View.VISIBLE
                            setOnClickListener {
                                val bridgesAuthModalFragment = BridgesAuthRequestModalFragment(Bridges
                                    .canPublish(requireContext())) {
                                    if(Bridges.canPublish(requireContext())) {
                                        val fragmentTransaction = activity?.supportFragmentManager
                                            ?.beginTransaction()
                                        val emailComposeModalFragment = EmailComposeModalFragment(
                                            Bridges.storedPlatformsEntity,
                                            isBridge = true
                                        ) {
                                            activity?.finish()
                                        }
                                        fragmentTransaction?.add(emailComposeModalFragment,
                                            "email_compose_tag")
                                        fragmentTransaction?.show(emailComposeModalFragment)
                                        fragmentTransaction?.commitNow()
                                    }
                                    else {
                                        startActivity(Intent(requireContext(),
                                            BridgesSubmitCodeActivity::class.java))
                                    }
                                }
                                bridgesAuthModalFragment.show(parentFragmentManager,
                                    "bridges_auth_tag")
                            }
                        }
                    }
                    else {
                        noRecentMessagesText.visibility = View.GONE
                        view.findViewById<View>(R.id.homepage_no_message_image).visibility = View.GONE

                        view.findViewById<View>(R.id.homepage_compose_new_btn).visibility = View.GONE
                        view.findViewById<View>(R.id.homepage_add_new_btn).visibility = View.GONE

                        view.findViewById<View>(R.id.homepage_compose_new_btn1).visibility = View.VISIBLE
                        view.findViewById<View>(R.id.homepage_add_new_btn1).visibility = View.VISIBLE

                        view.findViewById<View>(R.id.homepage_compose_new_btn1)
                            .setOnClickListener { v ->
                                showPlatformsModal(AvailablePlatformsModalFragment.Type.SAVED)
                            }

                        view.findViewById<View>(R.id.homepage_add_new_btn1)
                            .setOnClickListener { v ->
                                showPlatformsModal(AvailablePlatformsModalFragment.Type.AVAILABLE)
                            }

                        view.findViewById<MaterialButton>(R.id.homepage_bridges_auth_btn)
                            .visibility = View.GONE
                    }
                }

                recentRecyclerAdapter.messageOnClickListener.observe(viewLifecycleOwner, Observer {
                    if(it != null) {
                        recentRecyclerAdapter.messageOnClickListener.value = null
                        when(it.type) {
                            Platforms.Type.TEXT.type -> {
                                CoroutineScope(Dispatchers.Default).launch {
                                    startActivity(Intent(requireContext(),
                                        TextViewActivity::class.java).apply {
                                        val platform = Datastore.getDatastore(requireContext())
                                            .storedPlatformsDao().fetch(it.platformId);
                                        putExtra("id", platform.id)
                                        putExtra("platform_name", platform.name!!)
                                        putExtra("message_id", it.id)
                                        putExtra("type", it.type)
                                    })
                                }
                            }
                            Platforms.Type.EMAIL.type -> {
                                CoroutineScope(Dispatchers.Default).launch {
                                    startActivity(Intent(requireContext(),
                                        EmailViewActivity::class.java).apply {
                                            val platform = if(it.platformId == "0")
                                                Bridges.storedPlatformsEntity
                                            else Datastore.getDatastore(requireContext())
                                                .storedPlatformsDao().fetch(it.platformId);
                                            putExtra("id", platform.id)
                                            putExtra("platform_name", platform.name!!)
                                            putExtra("message_id", it.id)
                                            putExtra("type", it.type)
                                    })
                                }
                            }
                            Platforms.Type.MESSAGE.type -> {
                                CoroutineScope(Dispatchers.Default).launch {
                                    startActivity(Intent(requireContext(),
                                        MessageViewActivity::class.java).apply {
                                        val platform = Datastore.getDatastore(requireContext())
                                            .storedPlatformsDao().fetch(it.platformId);
                                        putExtra("id", platform.id)
                                        putExtra("platform_name", platform.name!!)
                                        putExtra("message_id", it.id)
                                        putExtra("type", it.type)
                                    })
                                }
                            }
                        }
                    }
                })
            }
        }

    }

    private fun showPlatformsModal(type: AvailablePlatformsModalFragment.Type) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        val platformsModalFragment = AvailablePlatformsModalFragment(type)
        fragmentTransaction?.add(platformsModalFragment, "store_platforms_tag")
        fragmentTransaction?.show(platformsModalFragment)
        activity?.runOnUiThread { fragmentTransaction?.commit() }
    }

    private fun showLoginModal() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        val loginModalFragment = LoginModalFragment(loginSuccessRunnable)
        fragmentTransaction?.add(loginModalFragment, "login_signup_login_vault_tag")
        fragmentTransaction?.show(loginModalFragment)
        fragmentTransaction?.commit()
    }

}