package com.crux.screens.home.ui.component

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crux.R
import com.crux.ui.model.TaskListWithCountUi
import com.crux.util.ALL_TASK_LISTS_ID
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenBottomSheetView(
    selectedTaskListId: Int,
    sheetState: SheetState,
    taskLists: ImmutableList<TaskListWithCountUi>,
    onDismissRequest: () -> Unit,
    onSelectTaskList: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ModalBottomSheet(
        modifier = modifier
            .statusBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        dragHandle = null,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        content = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                title = {
                    Text(
                        text = stringResource(R.string.home_screen_bottom_sheet_title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                            // you must additionally handle intended state cleanup, if any.
                            coroutineScope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            )
            LazyColumn {
                item {
                    HomeScreenBottomSheetListItemView(
                        icon = Icons.Default.Layers,
                        title = stringResource(R.string.home_screen_all_lists),
                        taskCount = taskLists.sumOf { it.taskCount },
                        isSelected = selectedTaskListId == ALL_TASK_LISTS_ID,
                        onClick = {
                            onSelectTaskList(ALL_TASK_LISTS_ID)
                            coroutineScope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                        }
                    )
                }
                items(taskLists) { taskList ->
                    HomeScreenBottomSheetListItemView(
                        title = taskList.taskList.name,
                        taskCount = taskList.taskCount,
                        isSelected = selectedTaskListId == taskList.taskList.id,
                        onClick = {
                            onSelectTaskList(taskList.taskList.id)
                            coroutineScope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                        }
                    )
                }
            }
        }
    )
}
