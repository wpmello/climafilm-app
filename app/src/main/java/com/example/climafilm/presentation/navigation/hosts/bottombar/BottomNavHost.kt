package com.example.climafilm.presentation.navigation.hosts.bottombar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.climafilm.presentation.features.clima.ClimaScreen
import com.example.climafilm.presentation.features.home.HomeScreen
import com.example.climafilm.presentation.features.ia.IAScreen
import com.example.climafilm.presentation.features.settings.SettingsScreen
import com.example.climafilm.presentation.navigation.hosts.moviedetail.movieDetailNavHost
import com.example.climafilm.presentation.navigation.routes.bottombar.BottomBarRoutes
import com.example.climafilm.presentation.navigation.routes.moviedetail.MovieDetailRoutes

@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomBarRoutes.Home.toString()
    ) {
        composable(BottomBarRoutes.Home.toString()) {
            HomeScreen(
                onNavigateToMovieDetail = { movieDetailId ->
                    navHostController.navigate(route = MovieDetailRoutes.MovieDetail(movieDetailId))
                }
            )
        }
       composable(BottomBarRoutes.IA.toString()) {
            IAScreen()
        }
        composable(BottomBarRoutes.Clima.toString()) {
            ClimaScreen(
                onNavigateToMovieDetail = { movieDetailId ->
                    navHostController.navigate(route = MovieDetailRoutes.MovieDetail(movieDetailId))
                }
            )
        }
        composable(BottomBarRoutes.Settings.toString()) {
            SettingsScreen()
        }
        movieDetailNavHost(navHostController)
    }
}