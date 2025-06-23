package com.intent.core.data.database.model

import androidx.room.Embedded
import com.intent.core.domain.model.TaskListWithCount

data class TaskListWithCountQueryResult(
    @Embedded
    val taskList: TaskListEntity,
    val taskCount: Int
) {
    fun toDomain() = TaskListWithCount(
        taskList = taskList.toDomain(),
        taskCount = taskCount
    )
}
