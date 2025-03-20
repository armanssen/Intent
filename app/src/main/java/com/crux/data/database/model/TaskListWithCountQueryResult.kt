package com.crux.data.database.model

import androidx.room.Embedded
import com.crux.domain.model.TaskListWithCount

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
