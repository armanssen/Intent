package com.crux.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.crux.domain.model.Task

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TaskListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = CASCADE // Delete tasks when a list is deleted
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val createdAt: Long,
    val isCompleted: Boolean = false,
    val listId: Int
) {

    fun toDomain() = Task(
        id = id,
        title = title,
        createdAt = createdAt,
        isCompleted = isCompleted,
        listId = listId
    )
}

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    createdAt = createdAt,
    isCompleted = isCompleted,
    listId = listId
)
