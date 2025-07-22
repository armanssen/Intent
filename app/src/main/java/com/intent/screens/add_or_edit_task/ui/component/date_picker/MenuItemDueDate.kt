package com.intent.screens.add_or_edit_task.ui.component.date_picker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intent.R
import com.intent.util.DateTimeUtils
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
internal fun MenuItemDueDate(
    dueDate: Long?,
    isNewTask: Boolean,
    onClick: () -> Unit,
    onClickRemove: () -> Unit,
    onDateSelect: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSuggestionChipsVisible by remember(isNewTask) { mutableStateOf(isNewTask) }

    Column {
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
                    onClick = {
                        onClickRemove()
                        isSuggestionChipsVisible = false
                    },
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
        if(dueDate == null && isSuggestionChipsVisible) {
            Row {
                Spacer(Modifier.width(16.dp))
                SuggestionChip(
                    onClick = {
                        isSuggestionChipsVisible = false
                        onDateSelect(
                            ZonedDateTime
                                .now(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli()
                        )
                    },
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    label = {
                        Text("Today")
                    }
                )
                Spacer(Modifier.width(8.dp))
                SuggestionChip(
                    onClick = {
                        isSuggestionChipsVisible = false
                        onDateSelect(
                            ZonedDateTime
                                .now(ZoneId.systemDefault())
                                .plusDays(1)
                                .toInstant()
                                .toEpochMilli()
                        )
                    },
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    label = {
                        Text("Tomorrow")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MenuItemDueDate(
        dueDate = null,
        isNewTask = true,
        onClick = {},
        onClickRemove = {},
        onDateSelect = {}
    )
}
