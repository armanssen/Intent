package com.crux.screens

import com.crux.domain.model.AppTheme

data class MainState(
    val isSplashScreenVisible: Boolean = true,
    val selectedAppTheme: AppTheme = AppTheme.SYSTEM_DEFAULT,
    val isDynamicColorEnabled: Boolean = false
)
