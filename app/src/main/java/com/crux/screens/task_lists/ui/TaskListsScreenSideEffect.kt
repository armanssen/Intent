package com.crux.screens.task_lists.ui

sealed interface TaskListsScreenSideEffect {

    data object NavigateBack : TaskListsScreenSideEffect
}
