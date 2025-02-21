package com.example.climafilm.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.climafilm.ui.fragments.components.OnBoardingViewPagerScreen
import com.example.climafilm.ui.fragments.components.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "Splash") {
        composable("Splash") {
            SplashScreen(
                onNavigateToHome = { navController.navigate(route = "Home") },
                onNavigateToOnboardingViewPager = { navController.navigate(route = "OnboardingViewPager") })
        }

        composable("home") {
            Text("Home Screen")
        }
        composable("onboarding") {
            OnBoardingViewPagerScreen( onFinishOnboarding = { navController.navigate(route = "Home") })
        }
    }
}