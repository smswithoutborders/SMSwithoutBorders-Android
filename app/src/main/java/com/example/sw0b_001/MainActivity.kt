package com.example.sw0b_001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.sw0b_001.ui.views.AboutView
import com.example.sw0b_001.ui.views.RecentsView
import com.example.sw0b_001.ui.views.SettingsView
import kotlinx.serialization.Serializable

@Serializable
object RecentScreen
@Serializable
object GatewayClientScreen
@Serializable
object AboutScreen
@Serializable
object SettingsScreen
@Serializable
object HomepageScreen
@Serializable
object HomepageNotLoggedScreen
@Serializable
object AddPlatformsScreen
@Serializable
object OtpCodeVerificationScreen
@Serializable
object SecurityScreen

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
                    NavHost(
                        modifier = Modifier,
                        navController = navController,
                        startDestination = RecentScreen,
                    ) {
                        composable<RecentScreen> {
                            RecentScreenComposable(navController = navController)
                        }
                        composable<SettingsScreen> {
                            SettingsScreenComposable()
                        }
                        composable<AboutScreen> {
                            AboutScreenComposable()
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun RecentScreenComposable(navController: NavHostController) {
    RecentsView(navController = navController)
}

@Composable
fun SettingsScreenComposable() {
    SettingsView()
}

@Composable
fun AboutScreenComposable() {
    AboutView()
}