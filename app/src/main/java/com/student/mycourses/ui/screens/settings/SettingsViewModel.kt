package com.student.mycourses.ui.screens.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val appVersion: String = "Version 5.1.2",
    val isPremium: Boolean = false,
    val selectedThemeColor: String = "#C8E6C0",
    val selectedLanguage: String = "English",
    val showThemeDialog: Boolean = false,
    val showLanguageDialog: Boolean = false
)

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun showThemeDialog() {
        _uiState.update { it.copy(showThemeDialog = true) }
    }

    fun hideThemeDialog() {
        _uiState.update { it.copy(showThemeDialog = false) }
    }

    fun setThemeColor(colorHex: String) {
        _uiState.update { it.copy(selectedThemeColor = colorHex, showThemeDialog = false) }
    }

    fun showLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = true) }
    }

    fun hideLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = false) }
    }

    fun setLanguage(language: String) {
        _uiState.update { it.copy(selectedLanguage = language, showLanguageDialog = false) }
    }
}
