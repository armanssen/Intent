package com.crux.screens.home.ui

import com.crux.ui.model.TaskUi

sealed interface HomeScreenEvent {

    data class OnCheckedChange(
        val task: TaskUi,
        val isChecked: Boolean
    ) : HomeScreenEvent

    data class OnSelectTaskList(
        val taskListId: Int
    ) : HomeScreenEvent
}
