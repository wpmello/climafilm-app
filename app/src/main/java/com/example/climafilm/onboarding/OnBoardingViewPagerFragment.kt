package com.example.climafilm.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.climafilm.databinding.FragmentOnBoardingViewPagerBinding
import com.example.climafilm.onboarding.screens.FindMovieIAHelperIntroductionFragment
import com.example.climafilm.onboarding.screens.FindMovieIntroductionFragment
import com.example.climafilm.onboarding.screens.WelcomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingViewPagerFragment : Fragment() {

    private var _binding: FragmentOnBoardingViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            FindMovieIntroductionFragment(),
            FindMovieIAHelperIntroductionFragment(),
            WelcomeFragment()
        )

        val adapter = OnBoardingViewPagerAdapter(fragmentList, childFragmentManager, lifecycle)

        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position -> }.attach()
    }
}