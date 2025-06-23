package com.intent.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.intent.util.DEFAULT_TASK_LIST_ID
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

const val APP_PREFERENCES_NAME = "app_preferences"

val Context.dataStorePreferences: DataStore<Preferences> by preferencesDataStore(
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
    val SELECTED_TASK_LIST_ID = intPreferencesKey("selected_task_list_id")
    val IS_HIDE_COMPLETED_TASKS_ENABLED = booleanPreferencesKey("is_hide_completed_tasks_enabled")
}

object PreferenceDefaultValues {
    const val SELECTED_TASK_LIST_ID = DEFAULT_TASK_LIST_ID
    const val HIDE_COMPLETED_TASK_DEFAULT_VALUE = true
    const val IS_DYNAMIC_COLOR_ENABLED_DEFAULT = true
}
