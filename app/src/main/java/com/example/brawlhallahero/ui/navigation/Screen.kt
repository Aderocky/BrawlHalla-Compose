package com.example.brawlhallahero.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailBrawl : Screen("home/{brawlId}") {
        fun createRoute(brawlId: Long) = "home/$brawlId"
    }
}