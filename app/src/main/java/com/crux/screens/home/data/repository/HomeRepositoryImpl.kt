package com.crux.screens.home.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.crux.core.data.database.AppDatabase
import com.crux.core.data.datastore.PreferenceDefaultValues
import com.crux.core.data.datastore.PreferenceDefaultValues.HIDE_COMPLETED_TASK_DEFAULT_VALUE
import com.crux.core.data.datastore.PreferenceKeys
import com.crux.core.domain.model.Task
import com.crux.core.domain.model.TaskListWithCount
import com.crux.screens.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl
@Inject constructor(
    private val database: AppDatabase,
    private val dataStorePreferences: DataStore<Preferences>
) : HomeRepository {

    override fun getAllTasksFlow(): Flow<List<Task>> {
        return database
            .taskEntityDao()
            .getAllTasksFlow()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override fun getAllTaskListsFlow(): Flow<List<TaskListWithCount>> {
        return database
            .taskListEntityDao()
            .getAllFlowWithTaskCount()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override suspend fun updateTaskCompletion(
        id: Int,
        isCompleted: Boolean
    ) {
        database
            .taskEntityDao()
            .updateTaskCompletion(
                id = id,
                isCompleted = isCompleted
            )
    }

    override suspend fun setSelectedTaskListId(taskListId: Int) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferenceKeys.SELECTED_TASK_LIST_ID] = taskListId
        }
    }

    override fun getSelectedTaskListIdFlow(): Flow<Int> {
        return dataStorePreferences.data.map {
            it[PreferenceKeys.SELECTED_TASK_LIST_ID]
                ?: PreferenceDefaultValues.SELECTED_TASK_LIST_ID
        }
    }

    override fun getIsHideCompletedTasksEnabled(): Flow<Boolean> {
        return dataStorePreferences.data.map {
            it[PreferenceKeys.IS_HIDE_COMPLETED_TASKS_ENABLED]
                ?: HIDE_COMPLETED_TASK_DEFAULT_VALUE
        }
    }

    override suspend fun updateIsHideCompletedTasksEnabled(value: Boolean) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferenceKeys.IS_HIDE_COMPLETED_TASKS_ENABLED] = value
        }
    }
}
