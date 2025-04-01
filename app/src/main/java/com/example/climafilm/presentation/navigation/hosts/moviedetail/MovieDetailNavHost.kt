package com.example.climafilm.presentation.navigation.hosts.moviedetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.climafilm.presentation.features.moviedetail.MovieDetailScreen
import com.example.climafilm.presentation.navigation.routes.moviedetail.MovieDetailRoutes

fun NavGraphBuilder.movieDetailNavHost(navController: NavHostController) {
    navigation<MovieDetailRoutes.Graph>(
        startDestination = MovieDetailRoutes.MovieDetail()
    ) {
        composable<MovieDetailRoutes.MovieDetail> { backStackEntry ->
            val movieDetail: MovieDetailRoutes.MovieDetail = backStackEntry.toRoute()
            MovieDetailScreen(
                movieDetail.movieId,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}