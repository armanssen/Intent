package com.crux.ui.add_or_edit_task.ui

import com.crux.ui.model.TaskUi

data class AddOrEditTaskScreenState(
    val task: TaskUi? = null,
    val textFieldValue: String = "",
    val isTextFieldIncorrect: Boolean = false
)
