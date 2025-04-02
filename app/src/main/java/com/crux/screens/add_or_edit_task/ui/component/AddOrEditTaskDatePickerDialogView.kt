package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskDatePickerDialogView(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
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
