package com.crux.screens.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.crux.R
import com.crux.screens.drawer.component.DrawerItemView
import kotlinx.coroutines.launch

@Composable
internal fun MainScreenDrawer(
    drawerState: DrawerState,
    onClickAppearance: () -> Unit,
    onClickTaskLists: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = modifier,
        drawerShape = RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = 4.dp,
            bottomEnd = 4.dp
        ),
        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        drawerState = drawerState
    ) {
        Column {
            Text(
                text = "Crux",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Black,
            )
            DrawerItemView(
                label = "Task Lists",
                icon = ImageVector.vectorResource(R.drawable.ic_note_stack),
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onClickTaskLists()
                }
            )
            DrawerItemView(
                label = "Appearance",
                icon = Icons.Outlined.ColorLens,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onClickAppearance()
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )
            DrawerItemView(
                label = "Send Feedback",
                icon = Icons.Outlined.Feedback,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            )
            DrawerItemView(
                label = "About",
                icon = Icons.Outlined.BookmarkBorder,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    }
}
