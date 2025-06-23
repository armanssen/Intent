package com.intent.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.intent.R

@Composable
fun AddOrEditTaskFloatingActionButtonView(
    onClickSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        modifier = modifier.imePadding(),
        onClick = onClickSave,
        containerColor = MaterialTheme.colorScheme.primary,
        icon = {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "done icon"
            )
        },
        text = {
            Text(text = stringResource(R.string.add_or_edit_task_screen_save_task))
        }
    )
}
