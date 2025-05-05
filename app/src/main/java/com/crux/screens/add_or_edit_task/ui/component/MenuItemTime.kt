package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crux.R
import com.crux.screens.add_or_edit_task.ui.formatDueTime
import com.crux.screens.add_or_edit_task.ui.isAllDay

@Composable
internal fun MenuItemTime(
    dueDate: Long,
    onClick: () -> Unit,
    onClickRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onClick,
            content = {
                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = "time icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )
        Text(
            text = if (isAllDay(dueDate)) {
                stringResource(R.string.add_or_edit_task_screen_time_not_set)
            } else {
                formatDueTime(dueDate)
            },
            modifier = Modifier.weight(1f)
        )
        if (!isAllDay(dueDate)) {
            IconButton(
                onClick = onClickRemove,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "close icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}
