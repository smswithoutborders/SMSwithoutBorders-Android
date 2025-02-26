package com.example.sw0b_001.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sw0b_001.R
import com.example.sw0b_001.ui.appbars.BottomNavBar
import com.example.sw0b_001.ui.modals.PlatformOptionsModal
import com.example.sw0b_001.ui.theme.AppTheme

data class PlatformData(
    val logo: Int,
    val platformName: String,
    val isActive: Boolean,
    val icon: Int = R.drawable.relaysms_icon_default_shape
)

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AvailablePlatformsView(
    navController: NavController,
) {
    var showModal by remember { mutableStateOf(false) }
    var showPlatformOptions by remember { mutableStateOf(false) }
    var selectedPlatform by remember { mutableStateOf<PlatformData?>(null) }
    val platforms = listOf(
        PlatformData(R.drawable.gmail, "Gmail", true),
        PlatformData(R.drawable.telegram, "Telegram", false),
        PlatformData(R.drawable.x_icon, "X", false)
    )

    val showPlatformOptionsModal = { platform: PlatformData ->
        selectedPlatform = platform
        showPlatformOptions = true
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Available Platforms") })
        },
        bottomBar = {
            BottomNavBar (
                navController = navController
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
//                Text(
//                    text = "Available Platforms",
//                    style = MaterialTheme.typography.headlineMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))

                val platforms = listOf(
                    PlatformData(R.drawable.gmail, "Gmail", true),
                    PlatformData(R.drawable.telegram, "Telegram", false),
                    PlatformData(R.drawable.x_icon, "X", false)
                )

                PlatformListContent(
                    platforms = platforms,
                    filterPlatforms = false,
                    onPlatformClick = showPlatformOptionsModal
                )
            }
        }

        if (showPlatformOptions) {
            PlatformOptionsModal(
                platform = selectedPlatform!!,
                onDismissRequest = { showPlatformOptions = false },
                isActive = selectedPlatform!!.isActive,
                isDefault = selectedPlatform!!.platformName == "RelaySMS",
                navController = navController
            )
        }
    }
}

@Composable
fun RelaySMSCard(
    onClick: (PlatformData) -> Unit
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(130.dp)
            .clickable { onClick(PlatformData(R.drawable.relaysms_icon_blue, "RelaySMS", true)) },
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
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlatformListContent(
    platforms: List<PlatformData>,
    filterPlatforms: Boolean = false,
    onPlatformClick: (PlatformData) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Use your RelaySMS account",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        RelaySMSCard(onPlatformClick)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Use your online accounts",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        val displayedPlatforms = if (filterPlatforms) {
            platforms.filter { it.isActive }
        } else {
            platforms
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 2
        ) {
            displayedPlatforms.forEach { platform ->
                PlatformCard(
                    logo = platform.logo,
                    platformName = platform.platformName,
                    modifier = Modifier.width(130.dp),
                    isActive = platform.isActive,
                    onClick = { onPlatformClick(platform) }
                )
            }
        }
    }
}

@Composable
fun PlatformCard(
    logo: Int,
    platformName: String,
    modifier: Modifier = Modifier,
    isActive: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(130.dp)
            .width(130.dp)
            .clickable { onClick() },
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
                colorFilter = if (!isActive) ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)) else null
            )
            if (isActive) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                        .align(Alignment.TopEnd)
                )
            }
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
        AvailablePlatformsView(
            navController = NavController(context = LocalContext.current)
        )
    }
}