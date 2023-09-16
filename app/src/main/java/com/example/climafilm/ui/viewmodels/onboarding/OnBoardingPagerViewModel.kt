package com.example.climafilm.ui.viewmodels.onboarding

import androidx.lifecycle.ViewModel

class OnBoardingPagerViewModel : ViewModel() {

    var currentPage: Int = 0

    fun isNextPageValid(totalPages: Int): Boolean {
        return currentPage < totalPages - 1
    }

    fun nextPage() {
        currentPage++
    }

    fun previousPage() {
        if (currentPage > 0) {
            currentPage--
        }
    }
}