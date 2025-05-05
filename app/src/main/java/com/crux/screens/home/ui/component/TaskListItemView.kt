package com.crux.screens.home.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.crux.R
import com.crux.screens.add_or_edit_task.ui.isAllDay
import com.crux.ui.model.TaskGroup
import com.crux.ui.model.TaskPreviewParameterProvider
import com.crux.ui.model.TaskUi
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun TaskListItemView(
    task: TaskUi,
    taskGroup: TaskGroup,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var isChecked by remember(task.isCompleted) {
        mutableStateOf(task.isCompleted)
    }

    val dueText = remember(task.dueDateTime, taskGroup) {
        formatDueDateTime(context, task.dueDateTime, taskGroup)
    }

    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = onClick
            )
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheckedChange(isChecked)
                },
                colors = CheckboxDefaults.colors().copy(
                    checkedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    checkedBoxColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    uncheckedBoxColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = task.title,
                style = LocalTextStyle.current.let { style ->
                    if (isChecked) {
                        style.copy(
                            textDecoration = TextDecoration.LineThrough,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    } else {
                        style
                    }
                }
            )
            dueText?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    }
}

fun formatDueDateTime(
    context: Context,
    dueDate: Long?,
    taskGroup: TaskGroup
): String? {
    if (dueDate == null) return null

    val zone = ZoneId.systemDefault()
    val zonedDateTime = Instant.ofEpochMilli(dueDate).atZone(zone)
    val dueDateOnly = zonedDateTime.toLocalDate()
    val today = ZonedDateTime.now(zone).toLocalDate()
    val yesterday = today.minusDays(1)

    val datePart = when (taskGroup) {
        TaskGroup.Overdue -> {
            when (dueDateOnly) {
                today -> context.getString(R.string.overdue_today)
                yesterday -> context.getString(R.string.overdue_yesterday)
                else -> zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMM, yyyy"))
            }
        }
        TaskGroup.Today,
        TaskGroup.Tomorrow,
        is TaskGroup.WeekDay -> null
        else -> {
            zonedDateTime.format(
                DateTimeFormatter.ofPattern("dd MMM, yyyy")
            )
        }
    }

    val timePart = if (!isAllDay(dueDate)) {
         zonedDateTime.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    } else {
        null
    }

    return when {
        datePart != null && timePart != null -> "$datePart • $timePart"
        datePart != null -> datePart
        timePart != null -> timePart
        else -> null
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(TaskPreviewParameterProvider::class) task: TaskUi
) {
    TaskListItemView(
        task = task,
        taskGroup = TaskGroup.Today,
        onClick = {},
        onCheckedChange = {}
    )
}
