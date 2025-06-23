package com.intent.screens.appearance.ui

import com.intent.core.domain.model.AppTheme

data class AppearanceScreenState(
    val isDynamicColorEnabled: Boolean = false,
    val selectedAppTheme: AppTheme = AppTheme.SYSTEM_DEFAULT
)
