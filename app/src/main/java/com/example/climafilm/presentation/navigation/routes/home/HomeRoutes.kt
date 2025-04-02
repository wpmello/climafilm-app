package com.example.climafilm.presentation.navigation.routes.home

import kotlinx.serialization.Serializable

sealed class HomeRoutes {

    @Serializable
    data object Graph : HomeRoutes()

    @Serializable
    data object Home : HomeRoutes()

    @Serializable
    data class MovieDetail(
        val movieId: Int = 0
    ) : HomeRoutes()
}