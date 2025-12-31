package com.veera.jetpackcompose

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Notification : Screen("notification", "Notification")
    object Reports : Screen("reports", "Reports")
    object Settings : Screen("settings", "Settings")
}