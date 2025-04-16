package com.crux.screens.add_or_edit_task.ui

internal sealed interface AddOrEditTaskScreenEvent {

    data class OnValueChange(
        val value: String
    ): AddOrEditTaskScreenEvent

    data object OnClickSave : AddOrEditTaskScreenEvent

    data object OnClickDelete : AddOrEditTaskScreenEvent

    data object OnDismissDeleteConfirmation : AddOrEditTaskScreenEvent

    data object OnConfirmDeleteConfirmation : AddOrEditTaskScreenEvent
}

internal sealed interface DueDateEvent : AddOrEditTaskScreenEvent {

    data object OnClickDueDate : DueDateEvent

    data object OnDismissDatePicker : DueDateEvent

    data object OnClickRemoveDueDate : DueDateEvent

    data class OnSelectDueDate(val date: Long) : DueDateEvent

    data object OnClickTime : DueDateEvent

    data object OnDismissTimePicker : DueDateEvent
}

internal sealed interface TaskListEvent : AddOrEditTaskScreenEvent {

    data class OnSelectTaskList(val id: Int) : TaskListEvent

    data object OnClickAddTaskList : TaskListEvent

    data object OnClickDismissAddTaskListDialog : TaskListEvent

    data object OnClickConfirmAddTaskList : TaskListEvent

    data class OnAddTextFieldValueChange(val value: String) : TaskListEvent
}
