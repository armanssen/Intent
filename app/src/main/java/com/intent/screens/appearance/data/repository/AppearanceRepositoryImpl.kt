package com.intent.screens.appearance.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.intent.core.data.datastore.PreferenceDefaultValues.IS_DYNAMIC_COLOR_ENABLED_DEFAULT
import com.intent.core.data.datastore.PreferenceKeys
import com.intent.core.domain.model.AppTheme
import com.intent.screens.appearance.domain.repository.AppearanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppearanceRepositoryImpl
@Inject constructor(
    private val dataStorePreferences: DataStore<Preferences>
) : AppearanceRepository {

    override suspend fun updateAppTheme(
        appTheme: AppTheme
    ) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferenceKeys.APP_THEME] = appTheme.name
        }
    }

    override fun getAppTheme(): Flow<AppTheme> {
        return dataStorePreferences.data.map {
            AppTheme.valueOf(it[PreferenceKeys.APP_THEME] ?: AppTheme.SYSTEM_DEFAULT.name)
        }
    }

    override suspend fun updateIsDynamicColorEnabled(
        isEnabled: Boolean
    ) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferenceKeys.IS_DYNAMIC_COLOR_ENABLED] = isEnabled
        }
    }

    override fun getIsDynamicColorEnabled(): Flow<Boolean> {
        return dataStorePreferences.data.map {
            it[PreferenceKeys.IS_DYNAMIC_COLOR_ENABLED]
                ?: IS_DYNAMIC_COLOR_ENABLED_DEFAULT
        }
    }
}
