package com.crux.ui.main.data.repository

import com.crux.data.database.AppDatabase
import com.crux.domain.model.Task
import com.crux.ui.main.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val database: AppDatabase
) : MainRepository {

    override suspend fun getAllTasks(): List<Task> {
        return database
            .taskEntityDao()
            .getAllTasks()
            .map {
                it.toDomain()
            }
    }
}
