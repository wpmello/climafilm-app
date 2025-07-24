package com.example.climafilm.presentation.viewmodels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climafilm.data.source.local.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow<SettingsUiState?>(null)
    val uiState: StateFlow<SettingsUiState?> = _uiState

    private val _isSheetOpened = MutableStateFlow(false)
    val isSheetOpened: StateFlow<Boolean> = _isSheetOpened

    var userNameField by mutableStateOf(TextFieldValue(""))
    var userName: String = ""
        private set

    init {
        viewModelScope.launch {
            delay(2000)
            val savedUserName = appPreferences.userName.first()
            val savedTheme = appPreferences.appTheme.first()
            val savedTemperatureUnit = appPreferences.temperatureUnit.first()
            val savedLanguage = appPreferences.language.first()

            userName = savedUserName
            userNameField = TextFieldValue(savedUserName)

            if (savedUserName.isEmpty()) {
                _isSheetOpened.value = true
            }

            _uiState.value = SettingsUiState(
                userName = savedUserName,
                theme = savedTheme,
                temperatureUnit = savedTemperatureUnit,
                language = savedLanguage
            )

            combine(
                appPreferences.userName,
                appPreferences.appTheme,
                appPreferences.temperatureUnit,
                appPreferences.language
            ) { userNamePref, theme, temperatureUnit, language ->
                userName = userNamePref
                userNameField = TextFieldValue(userNamePref)
                SettingsUiState(
                    userName = userNamePref,
                    theme = theme,
                    temperatureUnit = temperatureUnit,
                    language = language
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun updateUserName(name: TextFieldValue) {
        viewModelScope.launch {
            userNameField = name
            _uiState.update { it?.copy(userName = name.text) }
        }
    }

    fun updateTheme(theme: AppThemeOption) {
        viewModelScope.launch {
            appPreferences.setTheme(theme)
            _uiState.update { it?.copy(theme = theme) }
        }
    }

    fun updateTemperatureUnit(temperatureUnit: String) {
        viewModelScope.launch {
            appPreferences.setTemperatureUnit(temperatureUnit)
            _uiState.update { it?.copy(temperatureUnit = temperatureUnit) }
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            appPreferences.setLanguage(language)
            _uiState.update { it?.copy(language = language) }
        }
    }

    fun saveUserName() {
        userName = userNameField.text
        viewModelScope.launch {
            appPreferences.setUserName(userName)
        }
    }

    fun openSheet() {
        _isSheetOpened.value = true
    }

    fun closeSheet() {
        _isSheetOpened.value = false
    }

}

data class SettingsUiState(
    val userName: String = "",
    val theme: AppThemeOption = AppThemeOption.SYSTEM,
    val temperatureUnit: String = "Celsius",
    val language: String = "Português"
)

enum class AppThemeOption {
    SYSTEM, LIGHT, DARK
}

fun AppThemeOption.displayName(): String = when (this) {
    AppThemeOption.LIGHT -> "Claro"
    AppThemeOption.DARK -> "Escuro"
    AppThemeOption.SYSTEM -> "Sistema"
}