package com.crux.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crux.domain.model.Task

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val createdAt: Long,
    val isCompleted: Boolean = false
) {

    fun toDomain() = Task(
        id = id,
        title = title,
        createdAt = createdAt,
        isCompleted = isCompleted
    )
}
