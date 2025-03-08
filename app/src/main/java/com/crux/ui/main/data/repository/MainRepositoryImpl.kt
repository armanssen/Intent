package com.crux.ui.main.data.repository

import com.crux.data.database.AppDatabase
import com.crux.domain.model.Task
import com.crux.ui.main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val database: AppDatabase
) : MainRepository {

    override fun getAllTasksFlow(): Flow<List<Task>> {
        return database
            .taskEntityDao()
            .getAllTasksFlow()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override suspend fun updateTaskCompletion(
        id: Int,
        isCompleted: Boolean
    ) {
        database
            .taskEntityDao()
            .updateTaskCompletion(
                id = id,
                isCompleted = isCompleted
            )
    }
}
