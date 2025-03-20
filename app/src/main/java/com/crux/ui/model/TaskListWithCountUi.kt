package com.crux.ui.model

import com.crux.domain.model.TaskListWithCount

data class TaskListWithCountUi(
    val taskList: TaskListUi,
    val taskCount: Int
)

fun TaskListWithCount.toUi() = TaskListWithCountUi(
    taskList = taskList.toUi(),
    taskCount = taskCount
)
