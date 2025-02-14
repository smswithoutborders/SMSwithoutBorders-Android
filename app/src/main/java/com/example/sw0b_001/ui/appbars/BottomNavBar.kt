package com.example.sw0b_001.ui.appbars

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sw0b_001.ui.theme.AppTheme

@Composable
fun BottomNavBar(
    currentScreen: String,
    onRecentsClicked: () -> Unit,
    onCountriesClicked: () -> Unit
) {
    NavigationBar(
//        containerColor = MaterialTheme.colorScheme.primary,
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
                selectedIconColor = MaterialTheme.colorScheme.onSurface,
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
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
                selectedIconColor = MaterialTheme.colorScheme.onSurface,
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RelayBottomNavBarPreview() {
    AppTheme(darkTheme = false) {
        BottomNavBar (
            currentScreen = "Recents",
            onRecentsClicked = {},
            onCountriesClicked = {}
        )
    }
}