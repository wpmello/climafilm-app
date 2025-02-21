package com.example.climafilm.ui.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.climafilm.R
import com.example.climafilm.ui.fragments.components.SplashScreen

class SplashFragment : Fragment() {
    private lateinit var composeVIew: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeVIew = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeVIew.setContent {
            SplashScreen(
                onNavigateToHome = { findNavController().navigate(R.id.action_splashFragment_to_homeFragment) },
                onNavigateToOnboardingViewPager = { findNavController().navigate(R.id.action_splashFragment_to_onBoardingViewPagerFragment) }
            )
        }
    }
}
