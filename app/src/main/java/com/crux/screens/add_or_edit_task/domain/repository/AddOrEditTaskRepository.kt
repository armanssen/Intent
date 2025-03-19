package com.crux.screens.add_or_edit_task.domain.repository

import com.crux.domain.model.Task
import com.crux.domain.model.TaskList
import kotlinx.coroutines.flow.Flow

internal interface AddOrEditTaskRepository {

    suspend fun getTaskById(id: Int): Task?

    suspend fun insertTask(
        title: String,
        createdAt: Long,
        listId: Int
    )

    suspend fun updateTask(
        task: Task
    )

    suspend fun deleteTaskById(
        id: Int
    )

    fun getTaskLists(): Flow<List<TaskList>>
}
