package com.crux.screens.task_lists.domain.repository

import com.crux.domain.model.TaskListWithCount
import kotlinx.coroutines.flow.Flow

interface TaskListsRepository {

    fun getAllTaskListsFlow(): Flow<List<TaskListWithCount>>

    suspend fun updateTaskListName(
        id: Int,
        name: String
    )

    suspend fun addTaskList(
        name: String
    )

    suspend fun deleteTaskListById(
        id: Int
    )

    suspend fun setSelectedTaskListId(
        taskListId: Int
    )
}
