package com.crux.screens.task_lists.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.screens.task_lists.ui.component.AddTaskListDialogView
import com.crux.screens.task_lists.ui.component.TaskListsListItemView

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
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(text = "Task Lists")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onClickBack,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "arrow back"
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(TaskListsScreenEvent.OnClickAddTaskList)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "add icon"
                            )
                        }
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 12.dp
                )
            ) {
                items(uiState.taskLists) { taskList ->
                    TaskListsListItemView(
                        taskList = taskList,
                        taskCount = 12,
                        onClick = {

                        }
                    )
                }
            }
        }
    }

    if (uiState.isAddTaskListDialogVisible) {
        AddTaskListDialogView(
            textFieldValue = uiState.textFieldValue,
            onValueChange = {
                onEvent(TaskListsScreenEvent.OnValueChange(it))
            },
            onDismissRequest = {
                onEvent(TaskListsScreenEvent.OnClickDismissAddTaskListDialog)
            },
            onConfirmation = {
                onEvent(TaskListsScreenEvent.OnClickConfirmAddTaskList)
            }
        )
    }
}
