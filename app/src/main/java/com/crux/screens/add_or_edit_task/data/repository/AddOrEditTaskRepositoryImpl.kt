package com.crux.screens.add_or_edit_task.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.crux.data.database.AppDatabase
import com.crux.data.database.model.TaskEntity
import com.crux.data.database.model.TaskListEntity
import com.crux.data.database.model.toEntity
import com.crux.data.datastore.PreferenceDefaultValues
import com.crux.data.datastore.PreferenceKeys
import com.crux.domain.model.Task
import com.crux.domain.model.TaskList
import com.crux.screens.add_or_edit_task.domain.repository.AddOrEditTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddOrEditTaskRepositoryImpl
@Inject constructor(
    private val database: AppDatabase,
    private val dataStorePreferences: DataStore<Preferences>
) : AddOrEditTaskRepository {

    override fun getSelectedTaskListIdFlow(): Flow<Int> {
        return dataStorePreferences.data.map {
            it[PreferenceKeys.SELECTED_TASK_LIST_ID]
                ?: PreferenceDefaultValues.SELECTED_TASK_LIST_ID
        }
    }

    override suspend fun getTaskById(id: Int): Task? {
        return database
            .taskEntityDao()
            .getTaskById(taskId = id)
            ?.toDomain()
    }

    override suspend fun insertTask(task: Task) {
        database
            .taskEntityDao()
            .insert(
                task = task.toEntity()
            )
    }

    override suspend fun updateTask(task: Task) {
        database
            .taskEntityDao()
            .update(
                task = task.toEntity()
            )
    }

    override suspend fun deleteTaskById(id: Int) {
        database
            .taskEntityDao()
            .deleteById(id = id)
    }

    override fun getTaskLists(): Flow<List<TaskList>> {
        return database
            .taskListEntityDao()
            .getAllFlow().map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun addTaskList(name: String): Long {
        return database
            .taskListEntityDao()
            .insert(
                taskList = TaskListEntity(name = name)
            )
    }
}
