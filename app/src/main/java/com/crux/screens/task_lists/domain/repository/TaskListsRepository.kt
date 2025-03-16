package com.crux.screens.task_lists.domain.repository

import com.crux.domain.model.TaskList
import kotlinx.coroutines.flow.Flow

interface TaskListsRepository {

    fun getAllTaskListsFlow(): Flow<List<TaskList>>

    suspend fun updateTaskListName(
        id: Int,
        name: String
    )

    suspend fun addTaskList(
        name: String
    )
}
