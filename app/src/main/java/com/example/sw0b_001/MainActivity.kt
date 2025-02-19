package com.example.sw0b_001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sw0b_001.ui.theme.AppTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sw0b_001.Homepage.HomepageLoggedIn
import com.example.sw0b_001.Homepage.HomepageNotLoggedIn
import com.example.sw0b_001.Models.Messages.MessagesViewModel
import com.example.sw0b_001.ui.appbars.BottomNavBar
import com.example.sw0b_001.ui.navigation.Screen
import com.example.sw0b_001.ui.views.AboutView
import com.example.sw0b_001.ui.views.GatewayClientView
import com.example.sw0b_001.ui.views.RecentsView
import com.example.sw0b_001.ui.views.SettingsView
import kotlinx.serialization.Serializable

//@Serializable
//object RecentScreen
//@Serializable
//object GatewayClientScreen
//@Serializable
//object AboutScreen
//@Serializable
//object SettingsScreen
//@Serializable
//object HomepageScreen
//@Serializable
//object HomepageNotLoggedScreen
//@Serializable
//object AddPlatformsScreen
//@Serializable
//object OtpCodeVerificationScreen
//@Serializable
//object SecurityScreen

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    val messagesViewModel: MessagesViewModel = MessagesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                navController = rememberNavController()
                Surface(
                    Modifier
                        .fillMaxSize()
                ) {
                    MainNavigation(navController = navController)
//                    NavHost(
//                        modifier = Modifier,
//                        navController = navController,
//                        startDestination = RecentScreen,
//                    ) {
//                        composable<RecentScreen> {
//                            RecentScreenComposable()
//                        }
//                        composable<SettingsScreen> {
//                            SettingsScreenComposable()
//                        }
//                        composable<AboutScreen> {
//                            AboutScreenComposable()
//                        }
//                        composable<GatewayClientScreen> {
//                            GatewayClientsScreenComposable()
//                        }
//
//                    }
                }
            }
        }

    }

    @Composable
    fun MainNavigation(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Screen.Recents.route,
        ) {
            composable(Screen.Recents.route) {
                RecentsView(navController = navController)
            }
            composable(Screen.Settings.route) {
                SettingsView(navController = navController)
            }
            composable(Screen.About.route) {
                AboutView(navController = navController)
            }
            composable(Screen.GatewayClients.route) {
                GatewayClientView(navController = navController)
            }
        }
    }


//    @Composable
//    fun RecentScreenComposable() {
//        RecentsView(navController = navController)
//    }
//
//    @Composable
//    fun SettingsScreenComposable() {
//        SettingsView(navController = navController)
//    }
//
//    @Composable
//    fun AboutScreenComposable() {
//        AboutView(navController = navController)
//    }
//
//    @Composable
//    fun GatewayClientsScreenComposable() {
//        GatewayClientView(navController = navController)
//    }
}

