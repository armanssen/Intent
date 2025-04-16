package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskDatePickerDialogView(
    dueDate: Long?,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dueDate ?: System.currentTimeMillis()
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        shape = MaterialTheme.shapes.medium,
        confirmButton = {
            TextButton(
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it)
                    }
                    onDismiss()
                },
                content = {
                    Text("OK")
                }
            )
        },
        dismissButton = {
            TextButton(
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onDismiss()
                },
                content = {
                    Text("Cancel")
                }
            )
        },
        content = {
            DatePicker(state = datePickerState)
        }
    )
}
