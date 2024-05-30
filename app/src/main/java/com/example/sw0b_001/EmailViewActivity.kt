package com.example.sw0b_001

import android.os.Bundle
import com.example.sw0b_001.Database.Datastore
import com.example.sw0b_001.Models.ThreadExecutorPool
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView

class EmailViewActivity : AppCompactActivityCustomized() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_view)

        val myToolbar = findViewById<MaterialToolbar>(R.id.layout_email_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar?.apply {
            title = intent.getStringExtra("platform_name")
            setDisplayHomeAsUpEnabled(true)
        }
        configureView()
    }

    private fun configureView() {
        ThreadExecutorPool.executorService.execute {
            val message = Datastore.getDatastore(applicationContext).encryptedContentDAO()
                .get(intent.getLongExtra("message_id", -1))
            println(message.encryptedContent)
            runOnUiThread {
                message.encryptedContent.split(":").let {
                    findViewById<MaterialTextView>(R.id.layout_identity_header_title).apply {
                        text = "<${it[1]}>"
                    }
                    findViewById<MaterialTextView>(R.id.layout_identity_header_subject).apply {
                        text = "${getString(R.string.email_compose_cc)}: ${it[2]}\n" +
                                "${getString(R.string.email_compose_bcc)}: ${it[3]}"
                    }
                    findViewById<MaterialTextView>(R.id.layout_email_body).apply {
                        text = it.subList(5, it.size).joinToString()
                    }
                }
            }
        }
    }
}