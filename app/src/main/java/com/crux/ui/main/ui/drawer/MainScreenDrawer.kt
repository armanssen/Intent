package com.crux.ui.main.ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crux.ui.main.ui.drawer.component.DrawerItemView
import kotlinx.coroutines.launch

@Composable
internal fun MainScreenDrawer(
    drawerState: DrawerState,
    onClickAppearance: () -> Unit,
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
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerState = drawerState
    ) {
        Column {
            DrawerItemView(
                label = "Appearance",
                icon = Icons.Default.ColorLens,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onClickAppearance()
                }
            )
        }
    }
}
