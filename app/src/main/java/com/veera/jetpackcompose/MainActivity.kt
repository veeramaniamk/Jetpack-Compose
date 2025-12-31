package com.veera.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.veera.jetpackcompose.presentation.feature.home.CustomBottomBar
import com.veera.jetpackcompose.presentation.feature.home.Login
import com.veera.jetpackcompose.presentation.navigation.AppNavGraph

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

//        val vm = LoginViewModel()

        setContent {
            val navController = rememberNavController()
//            Scaffold(
//                contentWindowInsets = WindowInsets.systemBars,
//                bottomBar = {
//                    CustomBottomBar(navController)
//                }
//            ) { innerPadding ->
//                val topPadding = innerPadding.calculateTopPadding()
//                NavHost(
//                    navController = navController,
//                    startDestination = Screen.Home.route,
//                    modifier = Modifier
//                        .padding(top = topPadding)
//                ) {
//                    composable(Screen.Home.route) { HomeScreen() }
//                    composable(Screen.Notification.route) { NotificationScreen() }
//                    composable(Screen.Reports.route) { ReportsScreen() }
//                    composable(Screen.Settings.route) { SettingsScreen() }
//                }
//            }
            AppNavGraph(navController, /*vm*/)
        }

    }

}
