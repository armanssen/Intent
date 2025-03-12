package com.crux.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

const val APP_PREFERENCES_NAME = "app_preferences"

val Context.appPreferences: DataStore<Preferences> by preferencesDataStore(
    name = APP_PREFERENCES_NAME
)

suspend fun <T> DataStore<Preferences>.getPreference(
    preferenceKey: Preferences.Key<T>
): T? {
    return data.map { it[preferenceKey] }.firstOrNull()
}

object PreferenceKeys {
    val IS_DYNAMIC_COLOR_ENABLED = booleanPreferencesKey("is_dynamic_color_enabled")
    val APP_THEME = stringPreferencesKey("app_theme")
}

private object PreferenceDefaultValues {

}
