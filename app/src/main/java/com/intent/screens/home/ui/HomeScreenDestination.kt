package com.intent.screens.home.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.intent.screens.about.AboutScreenDestination
import com.intent.screens.add_or_edit_task.ui.AddOrEditTaskScreenDestination
import com.intent.screens.appearance.ui.AppearanceScreenDestination
import com.intent.screens.completed_tasks.ui.CompletedTasksScreenDestination
import com.intent.screens.drawer.DrawerNavigationEvent
import com.intent.screens.drawer.HomeScreenDrawer
import com.intent.screens.settings.ui.SettingsDestination
import com.intent.screens.task_lists.ui.TaskListsDestination
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenDestination

fun NavGraphBuilder.homeScreen(
    navController: NavController
) {
    composable<HomeScreenDestination>(
        popEnterTransition = {
            scaleIn(
                animationSpec = tween(300),
                initialScale = 0.97f
            )
        },
        exitTransition = {
            slideOutHorizontally { -it / 2 }
        }
    ) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                HomeScreenDrawer(
                    drawerState = drawerState,
                    onNavigationEvent = { navigationEvent ->
                        when (navigationEvent) {
                            DrawerNavigationEvent.NavigateToAbout -> {
                                navController.navigate(AboutScreenDestination)
                            }
                            DrawerNavigationEvent.NavigateToAppearance -> {
                                navController.navigate(AppearanceScreenDestination)
                            }
                            DrawerNavigationEvent.NavigateToCompletedTasks -> {
                                navController.navigate(CompletedTasksScreenDestination)
                            }
                            DrawerNavigationEvent.NavigateToSettings -> {
                                navController.navigate(SettingsDestination)
                            }
                            DrawerNavigationEvent.NavigateToTaskLists -> {
                                navController.navigate(TaskListsDestination)
                            }
                        }
                    }
                )
            }
        ) {
            HomeScreen(
                onClickAddNewTask = {
                    navController.navigate(AddOrEditTaskScreenDestination())
                },
                onClickTask = { taskId ->
                    navController.navigate(
                        AddOrEditTaskScreenDestination(taskId = taskId)
                    ) {
                        launchSingleTop = true
                    }
                },
                onClickMenu = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}
