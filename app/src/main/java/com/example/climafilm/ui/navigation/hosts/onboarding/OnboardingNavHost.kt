package com.example.climafilm.ui.navigation.hosts.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.climafilm.ui.fragments.components.OnBoardingViewPagerScreen
import com.example.climafilm.ui.fragments.components.SplashScreen
import com.example.climafilm.ui.navigation.hosts.home.homeNavHost
import com.example.climafilm.ui.navigation.routes.home.HomeRoutes
import com.example.climafilm.ui.navigation.routes.onboarding.OnboardingRoutes

@Composable
fun OnboardingNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = OnboardingRoutes.Splash
    ) {
        composable<OnboardingRoutes.Splash> {
            SplashScreen(
                onNavigateToHome = {
                    navHostController.popBackStack()
                    navHostController.navigate(route = HomeRoutes.Home) {
                        popUpTo(route = OnboardingRoutes.Splash) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToOnboardingViewPager = {
                    navHostController.popBackStack()
                    navHostController.navigate(route = OnboardingRoutes.OnboardingViewPager)
                }
            )
        }
        composable<OnboardingRoutes.OnboardingViewPager> {
            OnBoardingViewPagerScreen(
                onFinishOnboarding = {
                    navHostController.popBackStack()
                    navHostController.navigate(route = HomeRoutes.Home)
                }
            )
        }

        homeNavHost(navHostController = navHostController)
    }
}