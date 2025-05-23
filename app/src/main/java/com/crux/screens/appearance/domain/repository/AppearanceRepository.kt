package com.crux.screens.appearance.domain.repository

import com.crux.core.domain.model.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppearanceRepository {

    suspend fun updateAppTheme(appTheme: AppTheme)

    fun getAppTheme(): Flow<AppTheme>

    suspend fun updateIsDynamicColorEnabled(isEnabled: Boolean)

    fun getIsDynamicColorEnabled(): Flow<Boolean>
}
