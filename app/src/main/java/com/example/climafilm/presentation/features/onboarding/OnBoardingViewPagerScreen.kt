package com.example.climafilm.presentation.features.onboarding

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafilm.R
import com.example.climafilm.presentation.viewmodels.onboarding.OnboardingViewModel

@Composable
fun OnBoardingViewPagerScreen(
    onFinishOnboarding: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(
        "onBoardingFinished", Context.MODE_PRIVATE
    )
    val currentPage by viewModel.currentPage.collectAsState()
    val pages = viewModel.pages

    val pagerState = rememberPagerState(initialPage = currentPage) { pages.size }

    LaunchedEffect(key1 = currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
    ) { index ->
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                pages[index].invoke()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            viewModel.goToPreviousPage()
                        },
                        enabled = currentPage > 0
                    ) {
                        Text(text = stringResource(id = R.string.back))
                    }

                    Button(
                        onClick = {
                            if (currentPage < pages.size - 1) {
                                viewModel.goToNextPage()
                            } else {
                                onFinishOnboarding()
                                saveOnBoardingFinished(sharedPreferences)
                            }
                        }
                    ) {
                        Text(
                            text = if (currentPage == pages.size - 1)
                                stringResource(id = R.string.finish)
                            else
                                stringResource(id = R.string.next)
                        )
                    }
                }

            }
        }
    }
}

private fun saveOnBoardingFinished(sharedPreferences: SharedPreferences) {
    with(sharedPreferences.edit()) {
        putBoolean("Finished", true)
        apply()
    }
}