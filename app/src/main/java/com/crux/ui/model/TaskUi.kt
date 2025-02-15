package com.crux.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskUi(
    val id: Int,
    val title: String,
    val createdAt: Long,
    val isCompleted: Boolean
)
