package com.intent.screens.home.ui

import com.intent.core.ui.model.TaskUi

sealed interface HomeScreenEvent {

    data class OnCheckedChange(
        val task: TaskUi,
        val isChecked: Boolean
    ) : HomeScreenEvent

    data class OnSelectTaskList(
        val taskListId: Int
    ) : HomeScreenEvent

    data object OnClickHideCompletedTasks : HomeScreenEvent
}
