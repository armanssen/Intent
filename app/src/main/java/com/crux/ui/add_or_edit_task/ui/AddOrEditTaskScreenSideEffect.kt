package com.crux.ui.add_or_edit_task.ui

internal sealed interface AddOrEditTaskScreenSideEffect {

    data object TextFieldEmpty : AddOrEditTaskScreenSideEffect

    data object TaskSaved : AddOrEditTaskScreenSideEffect

    data object TaskDeleted : AddOrEditTaskScreenSideEffect
}
