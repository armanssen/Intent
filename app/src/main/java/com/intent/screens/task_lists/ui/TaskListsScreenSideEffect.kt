package com.intent.screens.task_lists.ui

sealed interface TaskListsScreenSideEffect {

    data object NavigateBack : TaskListsScreenSideEffect
}
