package com.crux.screens.task_lists.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.R
import com.crux.screens.task_lists.ui.component.AddOrEditTaskListDialogView
import com.crux.screens.task_lists.ui.component.DeleteTaskListDialogView
import com.crux.screens.task_lists.ui.component.TaskListsListItemView
import com.crux.screens.task_lists.ui.component.TaskListsTopAppBarView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskListsScreen(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskListsViewModel = hiltViewModel(),
    uiState: TaskListsScreenState = viewModel.uiState.collectAsStateWithLifecycle().value,
    onEvent: (TaskListsScreenEvent) -> Unit = viewModel::onEvent
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TaskListsTopAppBarView(
                scrollBehavior = scrollBehavior,
                onClickBack = onClickBack,
                onClickAddTaskList = {
                    onEvent(TaskListsScreenEvent.OnClickAddTaskList)
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp)
            ) {
                items(uiState.taskLists) { taskList ->
                    TaskListsListItemView(
                        taskList = taskList,
                        taskCount = 12,
                        onClick = {},
                        onClickEdit = {
                            onEvent(TaskListsScreenEvent.OnClickEdit(taskList))
                        },
                        onClickDelete = {
                            onEvent(TaskListsScreenEvent.OnClickDelete(taskList))
                        }
                    )
                }
            }
        }
    }

    if (uiState.isAddTaskListDialogVisible) {
        AddOrEditTaskListDialogView(
            icon = Icons.Outlined.Add,
            title = stringResource(R.string.task_lists_add_task_list_title),
            textFieldValue = uiState.addTextFieldValue,
            onValueChange = {
                onEvent(TaskListsScreenEvent.OnAddTextFieldValueChange(it))
            },
            onDismissRequest = {
                onEvent(TaskListsScreenEvent.OnClickDismissAddTaskListDialog)
            },
            onConfirmation = {
                onEvent(TaskListsScreenEvent.OnClickConfirmAddTaskList)
            }
        )
    }

    if (uiState.taskListForDeletion != null) {
        DeleteTaskListDialogView(
            taskListUi = uiState.taskListForDeletion,
            onDismissRequest = {
                onEvent(TaskListsScreenEvent.OnDismissDeleteConfirmation)
            },
            onClickConfirm = { id ->
                onEvent(TaskListsScreenEvent.OnConfirmDeleteConfirmation(id))
            }
        )
    }

    if (uiState.taskListForEdit != null) {
        AddOrEditTaskListDialogView(
            icon = Icons.Outlined.Edit,
            title = stringResource(R.string.task_lists_edit_task_list_title),
            textFieldValue = uiState.editTextFieldValue,
            onValueChange = {
                onEvent(TaskListsScreenEvent.OnEditTextFieldValueChange(it))
            },
            onDismissRequest = {
                onEvent(TaskListsScreenEvent.OnClickDismissEditTaskListDialog)
            },
            onConfirmation = {
                onEvent(TaskListsScreenEvent.OnConfirmEditConfirmation(uiState.taskListForEdit.id))
            }
        )
    }
}
