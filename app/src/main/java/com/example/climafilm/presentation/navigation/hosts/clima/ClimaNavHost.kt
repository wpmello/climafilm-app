package com.example.climafilm.presentation.navigation.hosts.clima

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.climafilm.presentation.features.clima.ClimaScreen
import com.example.climafilm.presentation.features.moviedetail.MovieDetailsScreen
import com.example.climafilm.presentation.navigation.routes.clima.ClimaRoutes
import com.example.climafilm.presentation.navigation.routes.moviedetail.MovieDetailRoutes

fun NavGraphBuilder.climaNavHost(navHostController: NavHostController) {
    navigation<ClimaRoutes.Graph>(
        startDestination = ClimaRoutes.Clima
    ) {
        composable<ClimaRoutes.Clima> {
            ClimaScreen(
                onNavigateToMovieDetail = { movieDetailId ->
                    navHostController.navigate(route = MovieDetailRoutes.MovieDetail(movieDetailId))
                }
            )
        }
        composable<ClimaRoutes.MovieDetail> { backStackEntry ->
            val movieDetail: MovieDetailRoutes.MovieDetail = backStackEntry.toRoute()
            MovieDetailsScreen(movieId = movieDetail.movieId, onBackPressed = {
                navHostController.popBackStack()
            })
        }
    }
}