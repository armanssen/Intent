package com.crux.screens.drawer

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crux.BuildConfig
import com.crux.R
import com.crux.screens.drawer.component.DrawerItemView
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreenDrawer(
    drawerState: DrawerState,
    onNavigationEvent: (DrawerNavigationEvent) -> Unit,
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
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                )
                Text(
                    text = "• ${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
            DrawerItemView(
                label = stringResource(R.string.drawer_task_lists),
                icon = Icons.Outlined.Layers,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onNavigationEvent(DrawerNavigationEvent.NavigateToTaskLists)
                }
            )
            DrawerItemView(
                label = stringResource(R.string.drawer_completed_tasks),
                icon = Icons.Outlined.CheckBox,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onNavigationEvent(DrawerNavigationEvent.NavigateToCompletedTasks)
                }
            )
            DrawerItemView(
                label = stringResource(R.string.drawer_appearance),
                icon = Icons.Outlined.ColorLens,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onNavigationEvent(DrawerNavigationEvent.NavigateToAppearance)
                }
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme
                    .onSurfaceVariant.copy(alpha = 0.2f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            DrawerItemView(
                label = stringResource(R.string.drawer_notifications),
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
                label = "Settings",
                icon = Icons.Outlined.Settings,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onNavigationEvent(DrawerNavigationEvent.NavigateToSettings)
                }
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme
                    .onSurfaceVariant.copy(alpha = 0.2f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            DrawerItemView(
                label = stringResource(R.string.drawer_send_feedback),
                icon = Icons.Outlined.Feedback,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    val email = "your-email@example.com" // Replace with your email
                    val subject = "Feedback for Task App"
                    val body = "Hello, I would like to share my feedback...\n"

                    val mIntent = Intent(Intent.ACTION_SENDTO)
                    mIntent.data = Uri.parse("mailto:")

                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    emailIntent.putExtra(Intent.EXTRA_TEXT, body)
                    emailIntent.selector = mIntent

                    try {
                        context.startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            )
            DrawerItemView(
                label = stringResource(R.string.drawer_about),
                icon = Icons.Outlined.BookmarkBorder,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onNavigationEvent(DrawerNavigationEvent.NavigateToAbout)
                }
            )
        }
    }
}
