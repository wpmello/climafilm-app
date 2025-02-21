package com.example.climafilm.ui.fragments.components

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.climafilm.R
import com.example.climafilm.ui.components.OnboardingScreen
import kotlinx.coroutines.launch

@Composable
fun OnBoardingViewPagerScreen(
    onFinishOnboarding: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sharedPreferences = LocalContext.current.getSharedPreferences(
        "onBoardingFinished", Context.MODE_PRIVATE
    )

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
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }

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
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        enabled = pagerState.currentPage > 0
                    ) {
                        Text(text = stringResource(id = R.string.back))
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (pagerState.currentPage < pages.size - 1) {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                } else {
                                    onFinishOnboarding()
                                    saveOnBoardingFinished(sharedPreferences)
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
    }
}

private fun saveOnBoardingFinished(sharedPreferences: SharedPreferences) {
    with(sharedPreferences.edit()) {
        putBoolean("Finished", true)
        apply()
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingViewPagerScreenPreview() {
    OnBoardingViewPagerScreen(
        onFinishOnboarding = {}
    )
}