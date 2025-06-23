package com.intent.core.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.intent.core.domain.model.TaskList

@Entity
data class TaskListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
) {

    fun toDomain() = TaskList(
        id = id,
        name = name
    )
}

fun TaskList.toEntity() = TaskListEntity(
    id = id,
    name = name
)
