package com.crux.screens.home.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.crux.screens.drawer.HomeScreenDrawer
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenDestination

fun NavGraphBuilder.homeScreen(
    onClickTaskLists: () -> Unit,
    onClickAppearance: () -> Unit,
    onClickAddNewTask: () -> Unit,
    onClickAbout: () -> Unit,
    onClickCompletedTasks: () -> Unit,
    onClickTask: (taskId: Int) -> Unit
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
                    onClickAppearance = onClickAppearance,
                    onClickTaskLists = onClickTaskLists,
                    onClickAbout = onClickAbout,
                    onClickCompletedTasks = onClickCompletedTasks
                )
            }
        ) {
            HomeScreen(
                onClickAddNewTask = onClickAddNewTask,
                onClickTask = onClickTask,
                onClickMenu = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}
