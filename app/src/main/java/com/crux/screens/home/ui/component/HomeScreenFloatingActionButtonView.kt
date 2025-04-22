package com.crux.screens.home.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crux.R

@Composable
internal fun MainScreenFloatingActionButtonView(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        expanded = expanded,
        containerColor = MaterialTheme.colorScheme.primary,
        icon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "add icon"
            )
        },
        text = {
            Text(
                text = stringResource(R.string.home_screen_new_task)
            )
        }
    )
}
