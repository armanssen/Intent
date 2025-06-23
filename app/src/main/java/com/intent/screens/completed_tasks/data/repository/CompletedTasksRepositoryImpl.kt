package com.intent.screens.completed_tasks.data.repository

import com.intent.core.data.database.AppDatabase
import com.intent.core.domain.model.Task
import com.intent.screens.completed_tasks.domain.repository.CompletedTasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompletedTasksRepositoryImpl
@Inject constructor(
    private val database: AppDatabase
) : CompletedTasksRepository {

    override fun getCompletedTasksFlow(): Flow<List<Task>> {
        return database
            .taskEntityDao()
            .getCompletedTasksFlow()
            .map { list ->
                list.map { it.toDomain() }
            }
    }
} 