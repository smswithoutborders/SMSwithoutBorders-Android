package com.example.sw0b_001.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sw0b_001.R
import com.example.sw0b_001.ui.theme.AppTheme

data class RecentMessage(
    val platformLogo: Int,
    val platformName: String,
    val messagePreview: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentsScreen(
    onComposeMessageClicked: () -> Unit,
    onSavePlatformsClicked: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // Sample recent messages data
    val recentMessages = listOf(
        RecentMessage(
            R.drawable.relaysms_icon_blue,
            "RelaySMS",
            "Hey, how are you doing? This is a test message.",
            "Today, 10:30 AM"
        ),
        RecentMessage(
            R.drawable.gmail,
            "Gmail",
            "New email from John Doe: Project Update",
            "Yesterday, 3:45 PM"
        ),
        RecentMessage(
            R.drawable.telegram,
            "Telegram",
            "New message in group: Team Meeting",
            "2 days ago, 9:00 AM"
        ),
        RecentMessage(
            R.drawable.x_icon,
            "X",
            "New tweet from Elon Musk: Exciting news!",
            "3 days ago, 11:15 AM"
        ),
        RecentMessage(
            R.drawable.relaysms_icon_blue,
            "RelaySMS",
            "Reminder: Don't forget to submit your report.",
            "4 days ago, 2:00 PM"
        ),
        RecentMessage(
            R.drawable.gmail,
            "Gmail",
            "New email from Jane Smith: Invoice attached",
            "5 days ago, 1:30 PM"
        ),
        RecentMessage(
            R.drawable.telegram,
            "Telegram",
            "New message in group: Project Discussion",
            "6 days ago, 10:00 AM"
        ),
        RecentMessage(
            R.drawable.x_icon,
            "X",
            "New tweet from Bill Gates: Tech advancements",
            "7 days ago, 4:00 PM"
        ),
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RelayAppBar(screenName = "Recents", isRecentsScreen = true)
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = onSavePlatformsClicked,
                    modifier = Modifier.padding(bottom = 16.dp),
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Download,
                        contentDescription = "Save Platforms",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                FloatingActionButton(
                    onClick = onComposeMessageClicked,
                    containerColor = MaterialTheme.colorScheme.tertiary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Compose Message",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(recentMessages) { message ->
                RecentMessageCard(message)
            }
        }
    }
}

@Composable
fun RecentMessageCard(message: RecentMessage) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { TODO("Add functionality for recent message card") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = message.platformLogo),
                contentDescription = "${message.platformName} Logo",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = message.platformName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = message.messagePreview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = message.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentsScreenPreview() {
    AppTheme(darkTheme = false) {
        RecentsScreen(onComposeMessageClicked = {}, onSavePlatformsClicked = {})
    }
}
