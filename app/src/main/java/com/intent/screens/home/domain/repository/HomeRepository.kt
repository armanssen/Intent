package com.intent.screens.home.domain.repository

import com.intent.core.domain.model.Task
import com.intent.core.domain.model.TaskListWithCount
import kotlinx.coroutines.flow.Flow

internal interface HomeRepository {

    fun getAllTasksFlow(): Flow<List<Task>>

    fun getAllTaskListsFlow(): Flow<List<TaskListWithCount>>

    suspend fun updateTaskCompletion(
        id: Int,
        isCompleted: Boolean
    )

    suspend fun setSelectedTaskListId(
        taskListId: Int
    )

    fun getSelectedTaskListIdFlow(): Flow<Int>

    fun getIsHideCompletedTasksEnabled(): Flow<Boolean>

    suspend fun updateIsHideCompletedTasksEnabled(value: Boolean)
}
