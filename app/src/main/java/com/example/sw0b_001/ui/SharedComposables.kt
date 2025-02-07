package com.example.sw0b_001.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sw0b_001.R
import com.example.sw0b_001.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelayAppBar(
    screenName: String,
    onBack: () -> Unit = {},
    isRecentsScreen: Boolean = false,
    onMenuClicked: () -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isDarkTheme = isSystemInDarkTheme()
    val logo = if (isDarkTheme) {
        R.drawable.relaysms_blue
    } else {
        R.drawable.relaysms_dark_theme_shape
    }
    CenterAlignedTopAppBar(
        title = {

            Image(
                painter = painterResource(id = logo),
                contentDescription = "Relay Logo",
                modifier = Modifier.size(120.dp)
            )
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (!isRecentsScreen) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                } else {
                    Text(
                        text = screenName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                }

            }
        },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = {
                        onMenuClicked()
                        showMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("About") },
                    onClick = {
                        onMenuClicked()
                        showMenu = false
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        scrollBehavior = scrollBehavior,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    )
}

@Composable
fun RelayBottomNavBar(
    currentScreen: String,
    onRecentsClicked: () -> Unit,
    onCountriesClicked: () -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        NavigationBarItem(
            icon = { Icon(
                Icons.Filled.Home,
                contentDescription = "Recents",
                modifier = Modifier.size(20.dp)
            ) },
            label = {
                Text(
                    text = "Recents",
                    style = MaterialTheme.typography.labelSmall
                )
                    },
            selected = currentScreen == "Recents",
            onClick = onRecentsClicked,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                indicatorColor = MaterialTheme.colorScheme.primary
            )
        )
        NavigationBarItem(
            icon = { Icon(
                Icons.Filled.Public,
                contentDescription = "Countries",
                modifier = Modifier.size(20.dp)
            ) },
            label = { Text(
                text = "Countries",
                style = MaterialTheme.typography.labelSmall
            ) },
            selected = currentScreen == "Countries",
            onClick = onCountriesClicked,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                indicatorColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RelayAppBarPreview() {
    AppTheme(darkTheme = true) {
        RelayAppBar(screenName = "Relay Screen", onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun RelayBottomNavBarPreview() {
    AppTheme(darkTheme = false) {
        RelayBottomNavBar(
            currentScreen = "Recents",
            onRecentsClicked = {},
            onCountriesClicked = {}
        )
    }
}