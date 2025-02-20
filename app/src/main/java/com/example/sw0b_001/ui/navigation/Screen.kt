package com.example.sw0b_001.ui.navigation

sealed class Screen(val route: String) {
    //Screen Routes
    data object Recents : Screen("recents")
    data object GatewayClients : Screen("gateway")
    data object About : Screen("about")
    data object Settings : Screen("settings")
    data object AddPlatforms : Screen("add_platforms")

    // Message Details Routes
    data class EmailDetails(val recentMessage: String = "{recentMessage}") : Screen("email_details/$recentMessage") {
        companion object {
        }
    }
    data class TelegramDetails(val recentMessage: String = "{recentMessage}") : Screen("telegram_details/$recentMessage") {
        companion object {
        }
    }
    data class XDetails(val recentMessage: String = "{recentMessage}") : Screen("x_details/$recentMessage") {
        companion object {
        }
    }
}