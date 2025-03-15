package com.example.climafilm.ui.navigation.routes.moviedetail

import kotlinx.serialization.Serializable

sealed class MovieDetailRoutes {

    @Serializable
    data object Graph : MovieDetailRoutes()

    @Serializable
    data class MovieDetail(
        val movieId: Int = 0
    ) : MovieDetailRoutes()
}