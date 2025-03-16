package com.crux.screens.task_lists.ui

sealed interface TaskListsScreenEvent {

    data object OnClickAddTaskList : TaskListsScreenEvent
    data object OnClickDismissAddTaskListDialog : TaskListsScreenEvent
    data object OnClickConfirmAddTaskList : TaskListsScreenEvent

    data class OnValueChange(
        val value: String
    ) : TaskListsScreenEvent
}
