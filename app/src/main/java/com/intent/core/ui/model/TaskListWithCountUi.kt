package com.intent.core.ui.model

import com.intent.core.domain.model.TaskListWithCount

data class TaskListWithCountUi(
    val taskList: TaskListUi,
    val taskCount: Int
)

fun TaskListWithCount.toUi() = TaskListWithCountUi(
    taskList = taskList.toUi(),
    taskCount = taskCount
)
