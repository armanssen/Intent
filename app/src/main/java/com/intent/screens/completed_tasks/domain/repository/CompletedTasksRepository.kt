package com.intent.screens.completed_tasks.domain.repository

import com.intent.core.domain.model.Task
import kotlinx.coroutines.flow.Flow

internal interface CompletedTasksRepository {

    fun getCompletedTasksFlow(): Flow<List<Task>>
}
