package com.crux.ui.appearance.ui

import com.crux.domain.model.AppTheme

data class AppearanceScreenState(
    val isDynamicColorEnabled: Boolean = false,
    val selectedAppTheme: AppTheme = AppTheme.SYSTEM_DEFAULT
)
