package com.example.climafilm.presentation.navigation.hosts.onboarding

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.climafilm.presentation.MainScreen
import com.example.climafilm.presentation.features.onboarding.OnBoardingViewPagerScreen
import com.example.climafilm.presentation.features.onboarding.SplashScreen
import com.example.climafilm.presentation.navigation.routes.bottombar.BottomBarRoutes
import com.example.climafilm.presentation.navigation.routes.onboarding.OnboardingRoutes

@Composable
fun OnboardingNavHost(navHostController: NavHostController, context: Context) {
    NavHost(
        navController = navHostController,
        startDestination = OnboardingRoutes.Splash
    ) {
        composable<OnboardingRoutes.Splash> {
            SplashScreen(
                onNavigateToHome = {
                    navHostController.popBackStack()
                    navHostController.navigate(route = BottomBarRoutes.Graph) {
                        popUpTo(route = OnboardingRoutes.Splash) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToOnboardingViewPager = {
                    navHostController.popBackStack()
                    navHostController.navigate(route = OnboardingRoutes.OnboardingViewPager)
                },
                context = context
            )
        }
        composable<OnboardingRoutes.OnboardingViewPager> {
            OnBoardingViewPagerScreen(
                onFinishOnboarding = {
                    navHostController.popBackStack()
                    navHostController.navigate(route = BottomBarRoutes.Graph)
                }
            )
        }

        composable<BottomBarRoutes.Graph> {
            MainScreen()
        }
    }
}