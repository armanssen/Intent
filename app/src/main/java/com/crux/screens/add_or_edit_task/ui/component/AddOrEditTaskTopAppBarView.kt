package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crux.R
import com.crux.ui.model.TaskUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskTopAppBarView(
    task: TaskUi?,
    onClickBack: () -> Unit,
    onClickDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (task == null) {
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
            task?.let { task ->
                IconButton(
                    onClick = {
                        onClickDelete(task.id)
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
