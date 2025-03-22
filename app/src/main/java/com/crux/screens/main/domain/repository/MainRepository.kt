package com.crux.screens.main.domain.repository

import com.crux.domain.model.Task
import com.crux.domain.model.TaskListWithCount
import kotlinx.coroutines.flow.Flow

internal interface MainRepository {

    fun getAllTasksFlow(): Flow<List<Task>>

    fun getAllTaskListsFlow(): Flow<List<TaskListWithCount>>

    suspend fun updateTaskCompletion(
        id: Int,
        isCompleted: Boolean
    )
}
