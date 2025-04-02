package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskTimePickerDialogView(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlertDialog(
        shape = MaterialTheme.shapes.medium,
        onDismissRequest = { },
        title = { },
        text = {
            TimePicker(
                state = timePickerState
            )
        },
        dismissButton = {
            TextButton(
                shape = MaterialTheme.shapes.medium,
                onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onConfirm()
                }
            ) {
                Text("OK")
            }
        },
        modifier = modifier
    )
}
