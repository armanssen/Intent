package com.crux.screens.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.crux.ui.model.TaskPreviewParameterProvider
import com.crux.ui.model.TaskUi

@Composable
internal fun TaskListItemView(
    task: TaskUi,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
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
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors().copy(
                    checkedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    checkedBoxColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    uncheckedBoxColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            text = task.title,
            style = LocalTextStyle.current.copy(
                textDecoration = if (task.isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )
        )
    }
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
