package com.crux.ui.model

data class TaskUi(
    val id: Int,
    val title: String,
    val createdAt: Long,
    val isCompleted: Boolean
)
