package com.crux.screens.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.R
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

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = "Default",
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    openBottomSheet = !openBottomSheet
                                }
                            )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onClickMenu()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menu icon"
                            )
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onClickAddNewTask,
                expanded = true,
                containerColor = MaterialTheme.colorScheme.primary,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add icon"
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.main_screen_new_task)
                    )
                }
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

    if (openBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            dragHandle = null,
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            content = {
                LazyColumn {
                    items(25) {
                        ListItem(
                            headlineContent = { Text("Item $it") },
                            leadingContent = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Localized description"
                                )
                            },
                            colors =
                                ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                                ),
                        )
                    }
                }
            }
        )
    }
}
