package com.example.climafilm.presentation.navigation.routes.clima

import kotlinx.serialization.Serializable

sealed class ClimaRoutes {

    @Serializable
    data object Graph : ClimaRoutes()

    @Serializable
    data object Clima: ClimaRoutes()

    @Serializable
    data class MovieDetail(
        val movieId: Int = 0
    ) : ClimaRoutes()
}