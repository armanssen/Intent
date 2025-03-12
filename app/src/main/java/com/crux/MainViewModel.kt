package com.crux

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.data.datastore.PreferenceKeys
import com.crux.data.datastore.appPreferences
import com.crux.domain.model.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainState())
    val uiState = _uiState.asStateFlow()

    init {
        collectAppTheme()
        collectIsDynamicColorEnabled()
        viewModelScope.launch {
            delay(SPLASH_DELAY) // little delay to make sure the theme is set
            _uiState.update {
                it.copy(isSplashScreenVisible = false)
            }
        }
    }

    private fun collectAppTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            context.appPreferences.data.map {
                AppTheme.valueOf(it[PreferenceKeys.APP_THEME] ?: AppTheme.SYSTEM_DEFAULT.name)
            }.collectLatest { appTheme ->
                _uiState.update {
                    it.copy(selectedAppTheme = appTheme)
                }
            }
        }
    }

    private fun collectIsDynamicColorEnabled() {
        viewModelScope.launch(Dispatchers.IO) {
            context.appPreferences.data.map {
                it[PreferenceKeys.IS_DYNAMIC_COLOR_ENABLED] ?: false
            }.collectLatest { isDynamicColorEnabled ->
                _uiState.update {
                    it.copy(isDynamicColorEnabled = isDynamicColorEnabled)
                }
            }
        }
    }

    companion object {
        private const val SPLASH_DELAY = 100L
    }
}
