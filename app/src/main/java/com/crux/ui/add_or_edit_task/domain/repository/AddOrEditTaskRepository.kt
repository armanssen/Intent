package com.crux.ui.add_or_edit_task.domain.repository

import com.crux.domain.model.Task

internal interface AddOrEditTaskRepository {

    suspend fun getTaskById(id: Int): Task?

    suspend fun insertTask(
        title: String,
        createdAt: Long
    )

    suspend fun updateTask(
        task: Task
    )
}
