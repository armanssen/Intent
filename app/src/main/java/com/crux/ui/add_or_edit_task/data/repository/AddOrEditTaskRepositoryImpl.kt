package com.crux.ui.add_or_edit_task.data.repository

import com.crux.data.database.AppDatabase
import com.crux.data.database.model.TaskEntity
import com.crux.data.database.model.toEntity
import com.crux.domain.model.Task
import com.crux.ui.add_or_edit_task.domain.repository.AddOrEditTaskRepository
import javax.inject.Inject

class AddOrEditTaskRepositoryImpl
@Inject constructor(
    private val database: AppDatabase
) : AddOrEditTaskRepository {

    override suspend fun getTaskById(id: Int): Task? {
        return database
            .taskEntityDao()
            .getTaskById(taskId = id)
            ?.toDomain()
    }

    override suspend fun insertTask(
        title: String,
        createdAt: Long
    ) {
        database
            .taskEntityDao()
            .insertTask(
                task = TaskEntity(
                    title = title,
                    createdAt = createdAt
                )
            )
    }

    override suspend fun updateTask(task: Task) {
        database
            .taskEntityDao()
            .updateTask(
                task  = task.toEntity()
            )
    }

    override suspend fun deleteTaskById(id: Int) {
        database
            .taskEntityDao()
            .deleteTaskById(id = id)
    }
}
