package com.crux.screens.task_lists.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.crux.ui.model.TaskListUi

@Composable
internal fun DeleteTaskListDialogView(
    taskListUi: TaskListUi,
    onDismissRequest: () -> Unit,
    onClickConfirm: (id: Int) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.DeleteOutline,
                    contentDescription = "delete icon"
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Are you sure?",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "All tasks from the list \"${taskListUi.name}\" will also be deleted"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    shape = RoundedCornerShape(4.dp),
                    onClick = onDismissRequest,
                    content = {
                        Text("Cancel")
                    }
                )
                Spacer(Modifier.width(4.dp))
                TextButton(
                    shape = RoundedCornerShape(4.dp),
                    onClick = {
                        onClickConfirm(taskListUi.id)
                    },
                    content = {
                        Text("Delete")
                    }
                )
            }
        }
    }
}
