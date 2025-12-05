package com.veera.jetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.veera.jetpackcompose.presentation.feature.home.HomePage
import com.veera.jetpackcompose.presentation.feature.home.Login
import com.veera.jetpackcompose.presentation.feature.home.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController, /*loginViewModel: LoginViewModel*/) {

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            Login(navController = navController, /*viewModel = loginViewModel*/)
        }

        composable("home") {
            HomePage()
        }
    }
}