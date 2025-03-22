package com.crux.screens.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.screens.main.ui.component.MainScreenBottomSheetView
import com.crux.screens.main.ui.component.MainScreenFloatingActionButtonView
import com.crux.screens.main.ui.component.MainScreenTopAppBarView
import com.crux.screens.main.ui.component.TaskListItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    onClickAddNewTask: () -> Unit,
    onClickMenu: () -> Unit,
    onClickTask: (taskId: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    uiState: MainScreenState = viewModel.uiState.collectAsStateWithLifecycle().value,
    onEvent: (MainScreenEvent) -> Unit = viewModel::onEvent
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            MainScreenTopAppBarView(
                selectedTaskListId = uiState.selectedTaskListId,
                taskLists = uiState.taskLists,
                scrollBehavior = scrollBehavior,
                onClickMenu = onClickMenu,
                onClickOpenBottomSheet = {
                    isBottomSheetVisible = !isBottomSheetVisible
                }
            )
        },
        floatingActionButton = {
            MainScreenFloatingActionButtonView(
                expanded = true,
                onClick = onClickAddNewTask
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
                content = {
                    items(uiState.tasks) { task ->
                        TaskListItemView(
                            task = task,
                            onClick = {
                                onClickTask(task.id)
                            },
                            onCheckedChange = { isChecked ->
                                onEvent(
                                    MainScreenEvent.OnCheckedChange(
                                        task = task,
                                        isChecked = isChecked
                                    )
                                )
                            }
                        )
                    }
                }
            )
        }
    }

    if (isBottomSheetVisible) {
        MainScreenBottomSheetView(
            selectedTaskListId = uiState.selectedTaskListId,
            sheetState = bottomSheetState,
            taskLists = uiState.taskLists,
            onDismissRequest = {
                isBottomSheetVisible = false
            },
            onSelectTaskList = { taskListId ->
                onEvent(MainScreenEvent.OnSelectTaskList(taskListId))
            }
        )
    }
}
