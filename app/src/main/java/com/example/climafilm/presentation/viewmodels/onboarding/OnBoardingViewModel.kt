package com.example.climafilm.presentation.viewmodels.onboarding

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.climafilm.R
import com.example.climafilm.presentation.features.onboarding.OnboardingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage

    val pages: List<@Composable () -> Unit> = listOf(
        {
            OnboardingScreen(
                imageResourceId = R.drawable.introduction_image,
                onBoardingTitleTextResourceId = R.string.now_playing_movies,
                onBoardingContentTextResourceId = R.string.find_movie_per_mood_description
            )
        },
        {
            OnboardingScreen(
                imageResourceId = R.drawable.introduction_image_ia_helper,
                onBoardingTitleTextResourceId = R.string.ia_helper,
                onBoardingContentTextResourceId = R.string.find_movie_ia_helper_description
            )
        },
        {
            OnboardingScreen(
                imageResourceId = R.drawable.introduction_image,
                onBoardingTitleTextResourceId = R.string.txt_welcome,
                onBoardingContentTextResourceId = R.string.welcome_description
            )
        }
    )

    fun goToNextPage() {
        _currentPage.value++
    }

    fun goToPreviousPage() {
        _currentPage.value--
    }

    fun goToPage(page: Int) {
        _currentPage.value = page
    }
}