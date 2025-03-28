package com.crux.screens.drawer

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crux.BuildConfig
import com.crux.screens.drawer.component.DrawerItemView
import kotlinx.coroutines.launch

@Composable
internal fun MainScreenDrawer(
    drawerState: DrawerState,
    onClickAppearance: () -> Unit,
    onClickTaskLists: () -> Unit,
    onClickAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Crux",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                )
                Text(
                    text = "v${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
            DrawerItemView(
                label = "Task Lists",
                icon = Icons.Outlined.Layers,
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
                color = MaterialTheme.colorScheme
                    .onSurfaceVariant.copy(alpha = 0.2f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            DrawerItemView(
                label = "Notifications",
                icon = Icons.Outlined.Notifications,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)

                    context.startActivity(intent)
                }
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
                    onClickAbout()
                }
            )
        }
    }
}
