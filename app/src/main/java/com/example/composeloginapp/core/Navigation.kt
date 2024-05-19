package com.example.composeloginapp.core

sealed class Route(val route: String) {
    data object Splash: Route("splash")
    data object Login: Route("login")
    data object Home: Route("home")
}
