package com.crux.screens.completed_tasks.ui.component

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.crux.ui.model.TaskUi
import com.crux.util.DateTimeUtils

@Composable
internal fun TaskItemView(
    task: TaskUi,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    var isChecked by remember(task.isCompleted) {
        mutableStateOf(task.isCompleted)
    }
    val dueText = remember(task.dueDateTime) {
        task.dueDateTime?.let {
            DateTimeUtils.formatDate(
                millis = it,
                formatPattern = "d MMM, yyyy"
            )
        }
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
