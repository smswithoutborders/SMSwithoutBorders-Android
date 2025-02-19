package com.example.sw0b_001.ui.navigation

sealed class Screen(val route: String) {
    data object Recents : Screen("recents")
    data object GatewayClients : Screen("gateway")
    data object About : Screen("about")
    data object Settings : Screen("settings")
    data class EmailDetails(val recentMessage: String = "{recentMessage}") : Screen("email_details/$recentMessage") {
        companion object {
            const val routeWithoutArgument = "email_details"
        }
    }
    data class TelegramDetails(val recentMessage: String = "{recentMessage}") : Screen("telegram_details/$recentMessage") {
        companion object {
            const val routeWithoutArgument = "telegram_details"
        }
    }
    data class XDetails(val recentMessage: String = "{recentMessage}") : Screen("x_details/$recentMessage") {
        companion object {
            const val routeWithoutArgument = "x_details"
        }
    }
}