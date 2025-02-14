package com.example.sw0b_001.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sw0b_001.R
import com.example.sw0b_001.ui.appbars.BottomNavBar
import com.example.sw0b_001.ui.appbars.RecentsAppBar
import com.example.sw0b_001.ui.theme.AppTheme

@Composable
fun HomepageView(
    onRecentsClicked: (() -> Unit),
    onCountriesClicked: () -> Unit,
    onSavePlatformsClicked: () -> Unit,
    onSendMessageClicked: () -> Unit,
    onComposeNewMessageClicked: () -> Unit,
    onMenuClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            RecentsAppBar(onMenuClicked = onMenuClicked)
        },
        bottomBar = {
            BottomNavBar (
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
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.no_recent),
                contentDescription = "Get Started Illustration",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )

            // No Recent Messages
            Text(
                text = "No recent messages",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(
//                    text = "Send your first message",
//                    style = MaterialTheme.typography.bodyMedium
//                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onComposeNewMessageClicked,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Compose New Message",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "New1 Message")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onSavePlatformsClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Download,
                            contentDescription = "Save Platforms",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Save Platforms")
                    }
                }
                Spacer(modifier = Modifier.height(64.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Help,
                        contentDescription = "Tutorial",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
//                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Need Help? View Tutorial",
//                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline
                    )
                }

            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme(darkTheme = false) {
        HomepageView(
            onRecentsClicked = {},
            onCountriesClicked = {},
            onMenuClicked = {},
            onSavePlatformsClicked = {},
            onSendMessageClicked = {},
            onComposeNewMessageClicked = {},
        )
    }
}