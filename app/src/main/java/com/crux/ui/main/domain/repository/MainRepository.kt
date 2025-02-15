package com.crux.ui.main.domain.repository

import com.crux.domain.model.Task

internal interface MainRepository {

    suspend fun getAllTasks(): List<Task>
}
