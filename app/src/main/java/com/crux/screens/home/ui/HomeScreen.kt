package com.crux.screens.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.R
import com.crux.screens.home.ui.component.HomeScreenBottomSheetView
import com.crux.screens.home.ui.component.MainScreenFloatingActionButtonView
import com.crux.screens.home.ui.component.MainScreenTopAppBarView
import com.crux.screens.home.ui.component.TaskListItemView
import com.crux.ui.model.TaskGroup
import com.crux.ui.model.TimeOfDay
import com.crux.ui.model.groupTasksByDueDateTime
import java.time.format.TextStyle
import java.util.Locale

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
                isHideCompletedTasksEnabled = uiState.isHideCompletedTasksEnabled,
                scrollBehavior = scrollBehavior,
                onClickMenu = onClickMenu,
                onClickOpenBottomSheet = {
                    isBottomSheetVisible = !isBottomSheetVisible
                },
                onClickHideCompletedTasks = {
                    onEvent(HomeScreenEvent.OnClickHideCompletedTasks)
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

            val groupedTasks = remember(uiState.tasks) {
                groupTasksByDueDateTime(uiState.tasks)
            }

            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
                content = {
                    groupedTasks.onEachIndexed { index, (group, tasksInGroup) ->
                        item {
                            if (index != 0) {
                                Spacer(Modifier.height(16.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = getTaskGroupLabel(group),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                )
                            }
                        }
                        items(
                            items = tasksInGroup,
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

fun getTaskGroupIcon(group: TaskGroup): ImageVector {
    return when (group) {
        TaskGroup.Overdue -> {
            Icons.Outlined.CalendarToday
        }
        is TaskGroup.Today -> {
            when (group.timeOfDay) {
                TimeOfDay.MORNING -> {
                    Icons.Outlined.WbTwilight
                }
                TimeOfDay.NOON -> {
                    Icons.Outlined.WbSunny
                }
                TimeOfDay.EVENING -> {
                    Icons.Outlined.Bedtime
                }
            }
        }
        else -> {
            Icons.Outlined.CalendarToday
        }
//        is TaskGroup.WeekDay -> TODO()
//        TaskGroup.NextWeek -> TODO()
//
//        TaskGroup.NextMonth -> TODO()
//        TaskGroup.SomeDay -> TODO()
//        TaskGroup.ThisMonth -> TODO()
//
//        TaskGroup.Tomorrow -> TODO()
//
//        TaskGroup.Later -> {
//
//        }
    }
}

@Composable
fun getTaskGroupLabel(group: TaskGroup): String {
    return when (group) {
        is TaskGroup.WeekDay -> {
            val dayName = group.day.getDisplayName(TextStyle.FULL, Locale.getDefault())
            stringResource(id = R.string.task_group_weekday, dayName)
        }
        else -> stringResource(id = group.labelResId)
    }
}
