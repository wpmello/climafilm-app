package com.example.climafilm.ui.navigation.routes.onboarding

import kotlinx.serialization.Serializable

sealed class OnboardingRoutes {

    @Serializable
    data object Splash: OnboardingRoutes()

    @Serializable
    data object OnboardingViewPager: OnboardingRoutes()
}