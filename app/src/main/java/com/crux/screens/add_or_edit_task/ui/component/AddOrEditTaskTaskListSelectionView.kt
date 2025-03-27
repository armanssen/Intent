package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.crux.ui.model.TaskListUi
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun AddOrEditTaskTaskListSelectionView(
    selectedTaskListId: Int,
    taskLists: ImmutableList<TaskListUi>,
    onSelectTaskList: (Int) -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Select task list",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            SelectionMenu(
                selectedTaskListId = selectedTaskListId,
                taskLists = taskLists,
                onSelectTaskList = onSelectTaskList,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = onClickAdd,
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add icon"
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectionMenu(
    selectedTaskListId: Int,
    taskLists: ImmutableList<TaskListUi>,
    onSelectTaskList: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        }
    ) {
        Row(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .clickable {}
                .padding(vertical = 16.dp)
                .padding(start = 16.dp, end = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Layers,
                contentDescription = "task lists icon"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = remember(selectedTaskListId, taskLists) {
                    taskLists.find {
                        it.id == selectedTaskListId
                    }?.name ?: "You have no task lists"
                },
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.width(8.dp))
            ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = expanded
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            taskLists.forEach { taskList ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = taskList.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onSelectTaskList(taskList.id)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Layers,
                            contentDescription = "task lists icon"
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
