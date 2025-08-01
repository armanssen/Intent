package com.intent.screens.task_lists.ui

import com.intent.core.ui.model.TaskListUi
import com.intent.core.ui.model.TaskListWithCountUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TaskListsScreenState(
    val isAddTaskListDialogVisible: Boolean = false,
    val addTextFieldValue: String = "",
    val taskLists: ImmutableList<TaskListWithCountUi> = persistentListOf(),
    val taskListForDeletion: TaskListUi? = null,
    val taskListForEdit: TaskListUi? = null,
    val editTextFieldValue: String = ""
)
