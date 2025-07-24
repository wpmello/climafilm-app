package com.example.climafilm.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.climafilm.presentation.viewmodels.settings.AppThemeOption
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "app_settings")

    private val USER_NAME = stringPreferencesKey("user_name")
    private val THEME = stringPreferencesKey("theme")
    private val TEMPERATURE_UNIT = stringPreferencesKey("temperature_unit")
    private val LANGUAGE = stringPreferencesKey("language")

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _appTheme = MutableStateFlow(AppThemeOption.SYSTEM)
    val appTheme: StateFlow<AppThemeOption> = _appTheme.asStateFlow()

    private val _temperatureUnit = MutableStateFlow("Celsius")
    val temperatureUnit: StateFlow<String> = _temperatureUnit.asStateFlow()

    private val _language = MutableStateFlow("Português")
    val language: StateFlow<String> = _language.asStateFlow()

    init {
        context.dataStore.data
            .onEach { preferences ->
                _userName.value = preferences[USER_NAME] ?: ""
                _appTheme.value = preferences[THEME]?.let {
                    runCatching { AppThemeOption.valueOf(it) }.getOrDefault(AppThemeOption.SYSTEM)
                } ?: AppThemeOption.SYSTEM
                _temperatureUnit.value = preferences[TEMPERATURE_UNIT] ?: "Celsius"
                _language.value = preferences[LANGUAGE] ?: "Português"
            }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { it[USER_NAME] = name }
    }

    suspend fun setTheme(theme: AppThemeOption) {
        context.dataStore.edit { it[THEME] = theme.name }
    }

    suspend fun setTemperatureUnit(unit: String) {
        context.dataStore.edit { it[TEMPERATURE_UNIT] = unit }
    }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { it[LANGUAGE] = language }
    }
}