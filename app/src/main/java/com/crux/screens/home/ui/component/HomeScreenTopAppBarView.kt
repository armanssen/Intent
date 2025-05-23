package com.crux.screens.home.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.crux.R
import com.crux.core.ui.model.TaskListWithCountUi
import com.crux.util.ALL_TASK_LISTS_ID
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenTopAppBarView(
    selectedTaskListId: Int,
    isHideCompletedTasksEnabled: Boolean,
    taskLists: ImmutableList<TaskListWithCountUi>,
    scrollBehavior: TopAppBarScrollBehavior,
    onClickMenu: () -> Unit,
    onClickOpenBottomSheet: () -> Unit,
    onClickHideCompletedTasks: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isDropdownMenuExpanded by rememberSaveable { mutableStateOf(false) }

    val title = remember(selectedTaskListId, taskLists) {
        if (selectedTaskListId == ALL_TASK_LISTS_ID) {
            context.getString(R.string.home_screen_all_lists)
        } else {
            taskLists.find {
                it.taskList.id == selectedTaskListId
            }?.taskList?.name ?: context.getString(R.string.home_screen_no_task_list_selected)
        }
    }

    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                modifier = Modifier
                    .clickable(onClick = onClickOpenBottomSheet)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(weight = 1f, fill = false)
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "arrow drop down icon"
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onClickMenu,
                content = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "menu icon"
                    )
                }
            )
        },
        actions = {
            Box {
                IconButton(
                    onClick = {
                        isDropdownMenuExpanded = true
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "more vert icon"
                        )
                    }
                )
                DropdownMenu(
                    expanded = isDropdownMenuExpanded,
                    onDismissRequest = { isDropdownMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Hide completed tasks",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = if (isHideCompletedTasksEnabled) {
                                    Icons.Default.CheckBox
                                } else {
                                    Icons.Default.CheckBoxOutlineBlank
                                },
                                contentDescription = "checkbox icon"
                            )
                        },
                        onClick = {
                            isDropdownMenuExpanded = false
                            onClickHideCompletedTasks()
                        }
                    )
                }
            }
        }
    )
}
