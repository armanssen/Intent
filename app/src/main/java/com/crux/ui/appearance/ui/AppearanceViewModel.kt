package com.crux.ui.appearance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.domain.model.AppTheme
import com.crux.ui.appearance.domain.repository.AppearanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AppearanceViewModel
@Inject constructor(
    private val repository: AppearanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppearanceScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        collectAppTheme()
        collectIsDynamicColorEnabled()
    }

    fun onEvent(event: AppearanceScreenEvent) {
        when (event) {
            is AppearanceScreenEvent.OnClickMaterialColors -> {
                updateIsDynamicColorEnabled(event.isChecked)
            }
            is AppearanceScreenEvent.OnSelectAppTheme -> {
                updateAppTheme(event.appTheme)
            }
        }
    }

    private fun collectAppTheme() {
        viewModelScope.launch {
            repository.getAppTheme()
                .collectLatest { appTheme ->
                    _uiState.update {
                        it.copy(
                            selectedAppTheme = appTheme
                        )
                    }
                }
        }
    }

    private fun updateAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            repository.updateAppTheme(appTheme = appTheme)
        }
    }

    private fun collectIsDynamicColorEnabled() {
        viewModelScope.launch {
            repository.getIsDynamicColorEnabled()
                .collectLatest { isDynamicColorEnabled ->
                    _uiState.update {
                        it.copy(
                            isDynamicColorEnabled = isDynamicColorEnabled
                        )
                    }
                }
        }
    }

    private fun updateIsDynamicColorEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            repository.updateIsDynamicColorEnabled(
                isEnabled = isEnabled
            )
        }
    }
}
