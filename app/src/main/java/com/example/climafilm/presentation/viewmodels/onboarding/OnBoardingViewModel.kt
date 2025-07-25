package com.example.climafilm.presentation.viewmodels.onboarding

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.example.climafilm.R
import com.example.climafilm.presentation.features.onboarding.OnboardingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    private val _currentPage = MutableStateFlow(0)

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
                imageResourceId = R.drawable.ia_assistant_like,
                onBoardingTitleTextResourceId = R.string.txt_welcome,
                onBoardingContentTextResourceId = R.string.welcome_description
            )
        }
    )

    fun updateCurrentPage(page: Int) {
        _currentPage.value = page
    }
}

object OnBoardingPreferences {
    private val ONBOARDING_FINISHED = booleanPreferencesKey(name = "onBoardingFinished")
    private val HAS_SEEN_DIALOG = booleanPreferencesKey(name = "has_seen_dialog")
    private val Context.dataStore by preferencesDataStore(name = "onboarding_prefs")

    suspend fun saveOnBoardingFinished(context: Context, finished: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_FINISHED] = finished
        }
    }

    fun readOnBoardingFinished(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_FINISHED] ?: false
        }
    }

    suspend fun setHasSeenDialog(context: Context, value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[HAS_SEEN_DIALOG] = value
        }
    }

    suspend fun hasSeenDialog(context: Context): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[HAS_SEEN_DIALOG] ?: false
    }
}
