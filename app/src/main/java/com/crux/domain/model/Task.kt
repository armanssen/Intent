package com.crux.domain.model

data class Task(
    val id: Int,
    val title: String,
    val createdAt: Long,
    val isCompleted: Boolean,
    val dueDateTime: Long?,
    val listId: Int
)
