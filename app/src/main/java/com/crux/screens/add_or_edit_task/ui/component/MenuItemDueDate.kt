package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
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
import com.crux.util.DateTimeUtils

@Composable
internal fun MenuItemDueDate(
    dueDate: Long?,
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
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = "calendar icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )
        Text(
            text = if (dueDate != null) {
                DateTimeUtils.formatDate(
                    millis = dueDate,
                    formatPattern = DateTimeUtils.FORMAT_FULL_DATE
                )
            } else {
                stringResource(R.string.add_or_edit_task_screen_due_date)
            },
            modifier = Modifier.weight(1f)
        )
        if (dueDate != null) {
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
