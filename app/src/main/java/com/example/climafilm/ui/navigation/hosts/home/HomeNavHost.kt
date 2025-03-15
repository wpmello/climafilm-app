package com.example.climafilm.ui.navigation.hosts.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.climafilm.ui.fragments.components.HomeScreen
import com.example.climafilm.ui.fragments.components.MovieDetailScreen
import com.example.climafilm.ui.navigation.hosts.moviedetail.movieDetailNavHost
import com.example.climafilm.ui.navigation.routes.home.HomeRoutes
import com.example.climafilm.ui.navigation.routes.moviedetail.MovieDetailRoutes

fun NavGraphBuilder.homeNavHost(navHostController: NavHostController) {
    navigation<HomeRoutes.Graph>(
        startDestination = HomeRoutes.Home
    ) {
        composable<HomeRoutes.Home> {
            HomeScreen(
                onNavigateToMovieDetail = { movieDetailId ->
                    navHostController.navigate(route = MovieDetailRoutes.MovieDetail(movieDetailId))
                })
        }
        composable<MovieDetailRoutes.MovieDetail> { backStackEntry ->
            val movieDetail: MovieDetailRoutes.MovieDetail = backStackEntry.toRoute()
            MovieDetailScreen(movieDetail.movieId, onBackPressed = { navHostController.popBackStack() })
        }

        movieDetailNavHost(navController = navHostController)
    }
}