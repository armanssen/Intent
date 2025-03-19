package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.crux.R
import com.crux.ui.model.TaskListUi
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskTaskListSelectionView(
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
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_note_stack),
                contentDescription = "task lists icon"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = remember(selectedTaskListId, taskLists) {
                    taskLists.find {
                        it.id == selectedTaskListId
                    }?.name ?: "Выберите"
                },
                modifier = Modifier.weight(1f)
            )
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
                        Text(text = taskList.name)
                    },
                    onClick = {
                        onSelectTaskList(taskList.id)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
