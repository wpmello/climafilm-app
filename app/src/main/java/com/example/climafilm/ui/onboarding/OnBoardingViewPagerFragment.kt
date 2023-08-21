package com.example.climafilm.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.climafilm.R
import com.example.climafilm.databinding.FragmentOnBoardingViewPagerBinding
import com.example.climafilm.ui.onboarding.screens.FindMovieIAHelperOnBoardingFragment
import com.example.climafilm.ui.onboarding.screens.FindMoviePerMoodOnBoardingFragment
import com.example.climafilm.ui.onboarding.screens.WelcomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingViewPagerFragment : Fragment() {

    private var _binding: FragmentOnBoardingViewPagerBinding? = null
    private val binding get() = _binding!!

    private val pagerViewModel: OnBoardingPagerViewModel by viewModels()

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
            FindMoviePerMoodOnBoardingFragment(),
            FindMovieIAHelperOnBoardingFragment(),
            WelcomeFragment()
        )

        val adapter = OnBoardingViewPagerAdapter(fragmentList, childFragmentManager, lifecycle)

        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position -> }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pagerViewModel.currentPage = position
                binding.backButton.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE

                when (position) {
                    0 -> {
                        binding.nextButton.text = getString(R.string.next)
                    }
                    2 -> {
                        binding.nextButton.text = getString(R.string.finish)
                    }
                    else -> {
                        binding.nextButton.text = getString(R.string.next)
                    }
                }
                super.onPageSelected(position)
            }
        })

        binding.nextButton.setOnClickListener {
            if (pagerViewModel.isNextPageValid(fragmentList.size)) {
                pagerViewModel.nextPage()
                binding.viewPager2.currentItem = pagerViewModel.currentPage
            } else {
                findNavController().navigate(R.id.action_onBoardingViewPagerFragment_to_homeFragment)
                onBoardingFinished()
            }
        }

        binding.backButton.setOnClickListener {
            pagerViewModel.previousPage()
            binding.viewPager2.currentItem = pagerViewModel.currentPage
        }
    }
    private fun onBoardingFinished() {
        val sharedPref = requireActivity()
            .getSharedPreferences("onBoardingFinished", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}