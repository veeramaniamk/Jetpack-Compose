package com.veera.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
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
            AppNavGraph(navController, /*vm*/)
        }

    }

}
