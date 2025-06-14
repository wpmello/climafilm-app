package com.example.climafilm.presentation.features.onboarding

import androidx.compose.foundation.background
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafilm.R
import com.example.climafilm.presentation.viewmodels.onboarding.OnBoardingPreferences
import com.example.climafilm.presentation.viewmodels.onboarding.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnBoardingViewPagerScreen(
    onFinishOnboarding: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pages = viewModel.pages

    val pagerState = rememberPagerState(initialPage = 0) { pages.size }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateCurrentPage(pagerState.currentPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                pages[index].invoke()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (pagerState.currentPage > 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                enabled = pagerState.currentPage > 0
            ) {
                Text(text = stringResource(id = R.string.back))
            }

            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            OnBoardingPreferences.saveOnBoardingFinished(context, true)
                            onFinishOnboarding()
                        }
                    }
                }
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1)
                        stringResource(id = R.string.finish)
                    else
                        stringResource(id = R.string.next)
                )
            }
        }
    }
}