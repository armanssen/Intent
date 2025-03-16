package com.crux.screens.task_lists.ui

import com.crux.ui.model.TaskListUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TaskListsScreenState(
    val isAddTaskListDialogVisible: Boolean = false,
    val textFieldValue: String = "",
    val taskLists: ImmutableList<TaskListUi> = persistentListOf(),
    val taskListForDeletion: TaskListUi? = null,
    val taskListForEdit: TaskListUi? = null

)
