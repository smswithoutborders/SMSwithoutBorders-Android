package com.example.sw0b_001.ui.views

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
import com.example.sw0b_001.ui.appbars.BottomNavBar
import com.example.sw0b_001.ui.appbars.RecentsAppBar
import com.example.sw0b_001.ui.theme.AppTheme

data class RecentMessage(
    val platformLogo: Int,
    val platformName: String,
    val headingText: String,
    val subHeadingText: String? = null,
    val messagePreview: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentsView(
    onComposeMessageClicked: () -> Unit,
    onSavePlatformsClicked: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // Sample recent messages data
    val recentMessages = listOf(
        RecentMessage(
            R.drawable.relaysms_icon_blue,
            "RelaySMS",
            "Just checking up",
            "jane.doe@gmail.com",
            "Hey, how are you doing? This is a test message.",
            "Today, 10:30 AM"
        ),
        RecentMessage(
            R.drawable.gmail,
            "Gmail",
            "Project Update",
            "john.doe@example.com",
            "Hi team, here's the latest update on the project...",
            "Yesterday, 3:45 PM"
        ),
        RecentMessage(
            R.drawable.telegram,
            "Telegram",
            "+23456789012",
            null,
            "New message in group: Team Meeting",
            "2 days ago, 9:00 AM"
        ),
        RecentMessage(
            R.drawable.x_icon,
            "X",
            "@elonmusk",
            null,
            "New tweet from Elon Musk: Exciting news!",
            "3 days ago, 11:15 AM"
        ),
        RecentMessage(
            R.drawable.relaysms_icon_blue,
            "RelaySMS",
            "Check out Ida's View",
            "ida@gmail.com",
            "Reminder: Don't forget to submit your report.",
            "4 days ago, 2:00 PM"
        ),
        RecentMessage(
            R.drawable.gmail,
            "Gmail",
            "Invoice attached",
            "jane.smith@example.com",
            "Please find the attached invoice for your review.",
            "5 days ago, 1:30 PM"
        ),
        RecentMessage(
            R.drawable.telegram,
            "Telegram",
            "+12345678901",
            null,
            "New message in group: Project Discussion",
            "6 days ago, 10:00 AM"
        ),
        RecentMessage(
            R.drawable.x_icon,
            "X",
            "@billgates",
            null,
            "New tweet from Bill Gates: Tech advancements",
            "7 days ago, 4:00 PM"
        ),
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecentsAppBar(onMenuClicked = {})
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
        },
        bottomBar = {
            BottomNavBar (
                currentScreen = "Recents",
                onRecentsClicked = { TODO("add functionality") },
                onCountriesClicked = { TODO("add functionality") }
            )
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
            .clickable { TODO("Handle card click") },
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
                // Heading Text
                Text(
                    text = message.headingText,
                    style = if (message.platformName == "Gmail") {
                        MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    } else {
                        MaterialTheme.typography.bodyLarge
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                // Subheading Text
                if (message.subHeadingText != null) {
                    Text(
                        text = message.subHeadingText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                // Message Preview
                Text(
                    text = message.messagePreview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // Date
            Text(
                text = message.date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecentsScreenPreview() {
    AppTheme(darkTheme = false) {
        RecentsView(onComposeMessageClicked = {}, onSavePlatformsClicked = {})
    }
}
