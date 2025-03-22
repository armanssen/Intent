package com.crux.screens.main.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.unit.dp
import com.crux.ui.model.TaskListWithCountUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenBottomSheetView(
    sheetState: SheetState,
    taskLists: ImmutableList<TaskListWithCountUi>,
    onDismissRequest: () -> Unit,
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
        containerColor = MaterialTheme.colorScheme.surface,
        content = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text("Select task list")
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
                                contentDescription = "close icon"
                            )
                        }
                    )
                }
            )
            LazyColumn {
                items(taskLists) { taskList ->
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {

                                }
                            )
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = taskList.taskList.name
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Tasks: ${taskList.taskCount}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    )
}
