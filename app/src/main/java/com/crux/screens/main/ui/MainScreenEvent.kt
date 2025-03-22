package com.crux.screens.main.ui

import com.crux.ui.model.TaskUi

sealed interface MainScreenEvent {

    data class OnCheckedChange(
        val task: TaskUi,
        val isChecked: Boolean
    ) : MainScreenEvent

    data class OnSelectTaskList(
        val taskListId: Int
    ) : MainScreenEvent
}
