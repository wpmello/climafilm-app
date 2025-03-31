package com.example.climafilm.presentation.navigation.routes.onboarding

import kotlinx.serialization.Serializable

sealed class OnboardingRoutes {

    @Serializable
    data object Splash: OnboardingRoutes()

    @Serializable
    data object OnboardingViewPager: OnboardingRoutes()
}