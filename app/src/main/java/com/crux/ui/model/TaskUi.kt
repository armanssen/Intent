package com.crux.ui.model

import com.crux.domain.model.Task
import kotlinx.serialization.Serializable

@Serializable
data class TaskUi(
    val id: Int,
    val title: String,
    val createdAt: Long,
    val isCompleted: Boolean
) {

    fun toDomain() = Task(
        id = id,
        title = title,
        createdAt = createdAt,
        isCompleted = isCompleted
    )
}

fun Task.toUi() = TaskUi(
    id = id,
    title = title,
    createdAt = createdAt,
    isCompleted = isCompleted
)
