package com.crux.ui.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crux.ui.main.ui.component.TaskListItemView

@Composable
internal fun MainScreen(
    onClickAddNewTask: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    uiState: MainScreenState = viewModel.uiState.collectAsState().value,
    onEvent: (MainScreenEvent) -> Unit = viewModel::onEvent
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(uiState.tasks) { task ->
                TaskListItemView(
                    task = task
                )
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            onClick = onClickAddNewTask,
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add icon"
                )
            },
            text = {
                Text(
                    text = "New task"
                )
            }
        )
    }
}
