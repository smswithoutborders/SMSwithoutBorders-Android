package com.example.sw0b_001.ui.theme

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.sw0b_001.Models.Messages.EncryptedContent
import com.example.sw0b_001.Models.Messages.MessagesViewModel


@Preview(showBackground = true)
@Composable
fun RecentMain(
    messagesViewModel: MessagesViewModel = MessagesViewModel()
) {
    val context = LocalContext.current

    val items: List<EncryptedContent> by messagesViewModel
        .getMessages(context).observeAsState(emptyList())

}