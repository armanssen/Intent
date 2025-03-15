package com.crux.screens.main.domain.repository

import com.crux.domain.model.Task
import kotlinx.coroutines.flow.Flow

internal interface MainRepository {

    fun getAllTasksFlow(): Flow<List<Task>>

    suspend fun updateTaskCompletion(
        id: Int,
        isCompleted: Boolean
    )
}
