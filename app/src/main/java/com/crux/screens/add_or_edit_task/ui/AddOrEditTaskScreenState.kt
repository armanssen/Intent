package com.crux.screens.add_or_edit_task.ui

import com.crux.ui.model.TaskListUi
import com.crux.ui.model.TaskUi
import com.crux.util.DEFAULT_TASK_LIST_ID
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AddOrEditTaskScreenState(
    val task: TaskUi? = null,
    val textFieldValue: String = "",
    val isTextFieldIncorrect: Boolean = false,
    val taskLists: ImmutableList<TaskListUi> = persistentListOf(),
    val selectedTaskListId: Int = DEFAULT_TASK_LIST_ID,
    val isAddTaskListDialogVisible: Boolean = false,
    val addTextFieldValue: String = ""
)
