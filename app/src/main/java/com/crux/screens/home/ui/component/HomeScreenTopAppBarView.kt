package com.crux.screens.home.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crux.ui.model.TaskListWithCountUi
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenTopAppBarView(
    selectedTaskListId: Int,
    taskLists: ImmutableList<TaskListWithCountUi>,
    scrollBehavior: TopAppBarScrollBehavior,
    onClickMenu: () -> Unit,
    onClickOpenBottomSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Box(
                modifier = Modifier
                    .clickable(onClick = onClickOpenBottomSheet)
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        text = taskLists.find {
                            it.taskList.id == selectedTaskListId
                        }?.taskList?.name ?: "No task list selected"
                    )
                }
            )
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
        }
    )
}
