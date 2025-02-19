package com.example.sw0b_001.ui.navigation

sealed class Screen(val route: String) {
    data object Recents : Screen("recents")
    data object GatewayClients : Screen("gateway")
    data object About : Screen("about")
    data object Settings : Screen("settings")
}