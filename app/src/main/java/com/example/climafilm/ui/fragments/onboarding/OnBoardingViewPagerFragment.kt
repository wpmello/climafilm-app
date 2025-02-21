package com.example.climafilm.ui.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.climafilm.R
import com.example.climafilm.ui.fragments.components.OnBoardingViewPagerScreen

class OnBoardingViewPagerFragment : Fragment() {
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            OnBoardingViewPagerScreen { findNavController().navigate(R.id.homeFragment) }
        }
    }
}

