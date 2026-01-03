package com.example.frontend_alp_vp.ui.navigation

sealed class Route(val path: String) {
    object Splash : Route("splash")
    object Login : Route("login")
    object Register : Route("register")
    object MainApp : Route("main_app")
}