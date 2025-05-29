package com.example.climafilm.presentation.navigation.routes.bottombar

import kotlinx.serialization.Serializable

sealed class BottomBarRoutes {
    @Serializable
    data object Graph : BottomBarRoutes()

    @Serializable
    data object Home : BottomBarRoutes()

    @Serializable
    data object IA : BottomBarRoutes()

    @Serializable
    data object Clima : BottomBarRoutes()

    @Serializable
    data object Settings : BottomBarRoutes()
}