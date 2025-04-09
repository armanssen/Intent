package com.crux.screens.add_or_edit_task.domain.repository

import com.crux.domain.model.Task
import com.crux.domain.model.TaskList
import kotlinx.coroutines.flow.Flow

internal interface AddOrEditTaskRepository {

    fun getSelectedTaskListIdFlow(): Flow<Int>

    suspend fun getTaskById(id: Int): Task?

    suspend fun insertTask(
        task: Task
    )

    suspend fun updateTask(
        task: Task
    )

    suspend fun deleteTaskById(
        id: Int
    )

    fun getTaskLists(): Flow<List<TaskList>>

    suspend fun addTaskList(
        name: String
    ): Long
}
