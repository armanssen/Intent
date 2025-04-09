package com.crux.screens.add_or_edit_task.ui

internal sealed interface AddOrEditTaskScreenEvent {

    data class OnValueChange(
        val value: String
    ): AddOrEditTaskScreenEvent

    data object OnClickSave : AddOrEditTaskScreenEvent

    data object OnClickDelete : AddOrEditTaskScreenEvent

    data object OnDismissDeleteConfirmation : AddOrEditTaskScreenEvent

    data object OnConfirmDeleteConfirmation : AddOrEditTaskScreenEvent

    data class OnSelectTaskList(
        val id: Int
    ) : AddOrEditTaskScreenEvent

    data object OnClickAddTaskList : AddOrEditTaskScreenEvent

    data object OnClickDismissAddTaskListDialog : AddOrEditTaskScreenEvent

    data object OnClickConfirmAddTaskList : AddOrEditTaskScreenEvent

    data class OnAddTextFieldValueChange(
        val value: String
    ) : AddOrEditTaskScreenEvent

    data object OnClickDueDate : AddOrEditTaskScreenEvent

    data object OnDismissDatePicker : AddOrEditTaskScreenEvent

    data class OnSelectDueDate(
        val date: Long
    ) : AddOrEditTaskScreenEvent

    data object OnClickTime : AddOrEditTaskScreenEvent

    data object OnDismissTimePicker : AddOrEditTaskScreenEvent
}
