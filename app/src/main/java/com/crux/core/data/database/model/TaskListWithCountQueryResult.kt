package com.crux.core.data.database.model

import androidx.room.Embedded
import com.crux.core.domain.model.TaskListWithCount

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
