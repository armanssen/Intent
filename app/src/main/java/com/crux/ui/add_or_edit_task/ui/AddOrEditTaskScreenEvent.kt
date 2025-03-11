package com.crux.ui.add_or_edit_task.ui

internal sealed interface AddOrEditTaskScreenEvent {

    data class OnValueChange(
        val value: String
    ): AddOrEditTaskScreenEvent

    data object OnClickSave : AddOrEditTaskScreenEvent

    data class OnClickDelete(
        val id: Int
    ) : AddOrEditTaskScreenEvent
}
