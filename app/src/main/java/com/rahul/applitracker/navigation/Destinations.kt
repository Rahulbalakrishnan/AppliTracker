package com.rahul.applitracker.navigation

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "Home"
}

object AddScreen : Destinations {
    override val route = "Add"
}

