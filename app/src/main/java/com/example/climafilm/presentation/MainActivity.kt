package com.example.climafilm.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.climafilm.data.source.local.AppPreferences
import com.example.climafilm.presentation.navigation.hosts.onboarding.OnboardingNavHost
import com.example.climafilm.presentation.viewmodels.settings.AppThemeOption
import com.example.climafilm.presentation.viewmodels.settings.SettingsViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            val isDarkTheme = when (uiState?.theme) {
                AppThemeOption.LIGHT -> false
                AppThemeOption.DARK -> true
                AppThemeOption.SYSTEM -> isSystemInDarkTheme()
                else -> false
            }

            MaterialTheme(
                colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
            ) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isDarkTheme

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }

                supportActionBar?.hide()

                OnboardingNavHost(
                    navHostController = rememberNavController(),
                    context = LocalContext.current
                )
            }
        }
    }
}