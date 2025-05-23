package com.crux.core.ui.model

import com.crux.core.domain.model.TaskListWithCount

data class TaskListWithCountUi(
    val taskList: TaskListUi,
    val taskCount: Int
)

fun TaskListWithCount.toUi() = TaskListWithCountUi(
    taskList = taskList.toUi(),
    taskCount = taskCount
)
