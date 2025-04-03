package com.crux.screens.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.screens.home.ui.component.HomeScreenBottomSheetView
import com.crux.screens.home.ui.component.MainScreenFloatingActionButtonView
import com.crux.screens.home.ui.component.MainScreenTopAppBarView
import com.crux.screens.home.ui.component.TaskListItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    onClickAddNewTask: () -> Unit,
    onClickMenu: () -> Unit,
    onClickTask: (taskId: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    uiState: HomeScreenState = viewModel.uiState.collectAsStateWithLifecycle().value,
    onEvent: (HomeScreenEvent) -> Unit = viewModel::onEvent
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    val lazyListState = rememberLazyListState()

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
                expanded = remember {
                    derivedStateOf { lazyListState.firstVisibleItemIndex == 0 }
                }.value,
                onClick = onClickAddNewTask
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
                content = {
                    items(
                        items = uiState.tasks,
                        key = { it.id }
                    ) { task ->
                        TaskListItemView(
                            task = task,
                            onClick = {
                                onClickTask(task.id)
                            },
                            onCheckedChange = { isChecked ->
                                onEvent(
                                    HomeScreenEvent.OnCheckedChange(
                                        task = task,
                                        isChecked = isChecked
                                    )
                                )
                            },
                            modifier = Modifier.animateItem()
                        )
                    }
                    item {
                        Spacer(Modifier.height(96.dp))
                    }
                }
            )
        }
    }

    if (isBottomSheetVisible) {
        HomeScreenBottomSheetView(
            selectedTaskListId = uiState.selectedTaskListId,
            sheetState = bottomSheetState,
            taskLists = uiState.taskLists,
            onDismissRequest = {
                isBottomSheetVisible = false
            },
            onSelectTaskList = { taskListId ->
                onEvent(HomeScreenEvent.OnSelectTaskList(taskListId))
            }
        )
    }
}
