package com.crux.screens.home.ui.component

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.crux.ui.model.TaskPreviewParameterProvider
import com.crux.ui.model.TaskUi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun TaskListItemView(
    task: TaskUi,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isChecked by rememberSaveable(task.isCompleted) {
        mutableStateOf(task.isCompleted)
    }

    val dueText by remember(task.dueDateTime) {
        mutableStateOf(formatDueDateTime(task.dueDateTime))
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

fun formatDueDateTime(timestamp: Long?): String? {
    if (timestamp == null) return null

    val dueDateTime = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val now = LocalDate.now()
    val dueDate = dueDateTime.toLocalDate()

    val datePart = when {
        dueDate.isEqual(now) -> "Today"
        dueDate.isEqual(now.plusDays(1)) -> "Tomorrow"
        dueDate.isBefore(now.plusWeeks(1)) -> dueDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()) // "Monday"
        else -> dueDateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy")) // "Apr 15, 2025"
    }

    val timePart = dueDateTime.format(DateTimeFormatter.ofPattern("h:mm a")) // "9:30 AM"

    return "$datePart at $timePart"
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(TaskPreviewParameterProvider::class) task: TaskUi
) {
    TaskListItemView(
        task = task,
        onClick = {},
        onCheckedChange = {}
    )
}
