package com.crux.screens.appearance.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.crux.core.data.datastore.PreferenceDefaultValues.IS_DYNAMIC_COLOR_ENABLED_DEFAULT
import com.crux.core.data.datastore.PreferenceKeys
import com.crux.core.domain.model.AppTheme
import com.crux.screens.appearance.domain.repository.AppearanceRepository
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
