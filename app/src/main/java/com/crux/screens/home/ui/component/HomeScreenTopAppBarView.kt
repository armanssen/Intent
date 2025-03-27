package com.crux.screens.home.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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
            Row(
                modifier = Modifier
                    .clickable(onClick = onClickOpenBottomSheet)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = taskLists.find {
                        it.taskList.id == selectedTaskListId
                    }?.taskList?.name ?: "No task list selected",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(weight = 1f, fill = false)
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "arrow drop down icon"
                )
            }
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
        },
        actions = {
            IconButton(
                onClick = {

                },
                content = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "more vert icon"
                    )
                }
            )
        }
    )
}
