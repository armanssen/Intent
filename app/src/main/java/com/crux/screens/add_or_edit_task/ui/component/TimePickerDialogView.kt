package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.crux.util.DateTimeUtils
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerDialogView(
    dueDate: Long?,
    onConfirm: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val initialHour = remember(dueDate) {
        dueDate?.let { DateTimeUtils.getDateHour(it) }
            ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }

    val initialMinute = remember(dueDate) {
        dueDate?.let { DateTimeUtils.getDateMinute(it) }
            ?: Calendar.getInstance().get(Calendar.MINUTE)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    AlertDialog(
        shape = MaterialTheme.shapes.medium,
        onDismissRequest = onDismiss,
        text = {
            TimePicker(
                state = timePickerState
            )
        },
        dismissButton = {
            TextButton(
                shape = MaterialTheme.shapes.medium,
                onClick = onDismiss,
                content = {
                    Text("Dismiss")
                }
            )
        },
        confirmButton = {
            TextButton(
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onConfirm(
                        timePickerState.hour,
                        timePickerState.minute
                    )
                    onDismiss()
                },
                content = {
                    Text("OK")
                }
            )
        },
        modifier = modifier
    )
}
