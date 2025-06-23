package com.intent.screens.task_lists.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.intent.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskListsTopAppBarView(
    scrollBehavior: TopAppBarScrollBehavior,
    onClickBack: () -> Unit,
    onClickAddTaskList: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.task_lists_screen_title)
            )
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
                onClick = onClickAddTaskList,
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add icon"
                    )
                }
            )
        }
    )
}
