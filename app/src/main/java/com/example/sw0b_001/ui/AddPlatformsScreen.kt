package com.example.sw0b_001.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sw0b_001.R
import com.example.sw0b_001.ui.theme.AppTheme

@Composable
fun AddPlatformsScreen(
    onRecentsClicked: (() -> Unit),
    onCountriesClicked: () -> Unit,
    onMenuClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            RelayAppBar(
                screenName = "Recents",
                isRecentsScreen = false,
                onMenuClicked = onMenuClicked
            )
        },
        bottomBar = {
            RelayBottomNavBar(
                currentScreen = "Recents",
                onRecentsClicked = onRecentsClicked,
                onCountriesClicked = onCountriesClicked
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Available Platforms",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Use your RelaySMS account",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                RelaySMSCard()

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Use your online accounts",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PlatformCard(
                        logo = R.drawable.gmail,
                        platformName = "Gmail",
                        modifier = Modifier.weight(1f)
                    )
                    PlatformCard(
                        logo = R.drawable.telegram,
                        platformName = "Telegram",
                        modifier = Modifier.weight(1f)
                    )
                    PlatformCard(
                        logo = R.drawable.x_icon,
                        platformName = "X",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun RelaySMSCard() {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(130.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.relaysms_icon_blue),
                contentDescription = "RelaySMS Logo",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.Green)
                    .align(Alignment.TopEnd)
            )
            Text(
                text = "RelaySMS account",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }
}

@Composable
fun PlatformCard(logo: Int, platformName: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(130.dp)
            .width(130.dp)
            .clickable{ TODO("Add functionality for platform card") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = logo),
                contentDescription = "$platformName Logo",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
            )
            Text(
                text = platformName,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPlatformsScreenPreview() {
    AppTheme(darkTheme = false) {
        AddPlatformsScreen(
            onRecentsClicked = {},
            onCountriesClicked = {},
            onMenuClicked = {},
        )
    }
}