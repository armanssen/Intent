package com.intent.screens.task_lists.ui

import com.intent.core.ui.model.TaskListUi

sealed interface TaskListsScreenEvent {

    data class OnClickTaskList(
        val taskList: TaskListUi
    ) : TaskListsScreenEvent

    // Add new
    data object OnClickAddTaskList : TaskListsScreenEvent
    data object OnClickDismissAddTaskListDialog : TaskListsScreenEvent
    data object OnClickConfirmAddTaskList : TaskListsScreenEvent

    data class OnAddTextFieldValueChange(
        val value: String
    ) : TaskListsScreenEvent

    // Delete
    data class OnClickDelete(
        val taskList: TaskListUi
    ) : TaskListsScreenEvent

    data object OnDismissDeleteConfirmation : TaskListsScreenEvent

    data class OnConfirmDeleteConfirmation(
        val taskListId: Int
    ) : TaskListsScreenEvent

    // Edit
    data class OnClickEdit(
        val taskList: TaskListUi
    ) : TaskListsScreenEvent

    data class OnEditTextFieldValueChange(
        val value: String
    ) : TaskListsScreenEvent

    data object OnClickDismissEditTaskListDialog : TaskListsScreenEvent

    data class OnConfirmEditConfirmation(
        val taskListId: Int
    ) : TaskListsScreenEvent
}
