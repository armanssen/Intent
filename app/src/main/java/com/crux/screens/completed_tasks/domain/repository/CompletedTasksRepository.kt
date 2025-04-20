package com.crux.screens.completed_tasks.domain.repository

import com.crux.domain.model.Task
import kotlinx.coroutines.flow.Flow

internal interface CompletedTasksRepository {

    fun getCompletedTasksFlow(): Flow<List<Task>>
}
