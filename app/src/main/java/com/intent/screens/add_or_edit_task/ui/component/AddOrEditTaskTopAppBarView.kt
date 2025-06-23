package com.intent.screens.add_or_edit_task.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DeleteOutline
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
internal fun AddOrEditTaskTopAppBarView(
    isEditTask: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onClickBack: () -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            if (!isEditTask) {
                Text(
                    text = stringResource(R.string.add_or_edit_task_screen_new_task_title)
                )
            }
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
            if (isEditTask) {
                IconButton(
                    onClick = {
                        onClickDelete()
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = "delete icon"
                        )
                    }
                )
            }
        }
    )
}
