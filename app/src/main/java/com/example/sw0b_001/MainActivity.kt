package com.example.sw0b_001

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import com.example.compose.AppTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sw0b_001.Models.Messages.MessagesViewModel
import kotlinx.serialization.Serializable
import kotlin.getValue

@Serializable
object RecentScreen

@Serializable
object GatewayClientScreen

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavHostController
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

                        }
                    }
                }
            }
        }

    }
}