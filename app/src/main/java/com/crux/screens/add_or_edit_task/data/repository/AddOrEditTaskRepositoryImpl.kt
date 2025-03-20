package com.crux.screens.add_or_edit_task.data.repository

import com.crux.data.database.AppDatabase
import com.crux.data.database.model.TaskEntity
import com.crux.data.database.model.TaskListEntity
import com.crux.data.database.model.toEntity
import com.crux.domain.model.Task
import com.crux.domain.model.TaskList
import com.crux.screens.add_or_edit_task.domain.repository.AddOrEditTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddOrEditTaskRepositoryImpl
@Inject constructor(
    private val database: AppDatabase
) : AddOrEditTaskRepository {

    override suspend fun getTaskById(id: Int): Task? {
        return database
            .taskEntityDao()
            .getTaskById(taskId = id)
            ?.toDomain()
    }

    override suspend fun insertTask(
        title: String,
        createdAt: Long,
        listId: Int
    ) {
        database
            .taskEntityDao()
            .insert(
                task = TaskEntity(
                    title = title,
                    createdAt = createdAt,
                    listId = listId
                )
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
