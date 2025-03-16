package com.crux.screens.task_lists.ui

import com.crux.ui.model.TaskListUi

sealed interface TaskListsScreenEvent {

    data object OnClickAddTaskList : TaskListsScreenEvent
    data object OnClickDismissAddTaskListDialog : TaskListsScreenEvent
    data object OnClickConfirmAddTaskList : TaskListsScreenEvent

    data class OnValueChange(
        val value: String
    ) : TaskListsScreenEvent

    data class OnClickDelete(
        val taskList: TaskListUi
    ) : TaskListsScreenEvent

    data object OnDismissDeleteConfirmation : TaskListsScreenEvent

    data class OnConfirmDeleteConfirmation(
        val taskListId: Int
    ) : TaskListsScreenEvent
}
