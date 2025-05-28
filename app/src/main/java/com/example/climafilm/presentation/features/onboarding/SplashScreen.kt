package com.example.climafilm.presentation.features.onboarding

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.climafilm.R
import com.example.climafilm.util.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit, onNavigateToOnboardingViewPager: () -> Unit, context: Context) {
    val isFinished by OnBoardingPreferences.readOnBoardingFinished(context).collectAsState(initial = false)

    LaunchedEffect(Unit) {
        delay(3000L)
        if (isFinished) {
            onNavigateToHome.invoke()
        } else {
            onNavigateToOnboardingViewPager.invoke()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.ic_inital_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.Black,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.quicksand_bold)),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

object OnBoardingPreferences {
    private val ONBOARDING_FINISHED = booleanPreferencesKey("onBoardingFinished")

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
}