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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MainScreenDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
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
            Text("Hello I'm drawer")
            NavigationDrawerItem(
                label = {
                    Text("Appearance")
                },
                onClick = {

                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ColorLens,
                        contentDescription = "color lens icon"
                    )
                },
                selected = false
            )
        }
    }
}
