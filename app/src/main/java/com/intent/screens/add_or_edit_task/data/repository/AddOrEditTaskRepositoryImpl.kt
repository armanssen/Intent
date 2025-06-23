package com.intent.screens.add_or_edit_task.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.intent.core.data.database.AppDatabase
import com.intent.core.data.database.model.TaskListEntity
import com.intent.core.data.database.model.toEntity
import com.intent.core.data.datastore.PreferenceDefaultValues
import com.intent.core.data.datastore.PreferenceKeys
import com.intent.core.domain.model.Task
import com.intent.core.domain.model.TaskList
import com.intent.screens.add_or_edit_task.domain.repository.AddOrEditTaskRepository
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
