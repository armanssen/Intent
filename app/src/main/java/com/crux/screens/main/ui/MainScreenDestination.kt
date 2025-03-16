package com.crux.screens.main.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.crux.screens.drawer.MainScreenDrawer
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object MainScreenDestination

fun NavGraphBuilder.mainScreen(
    onClickTaskLists: () -> Unit,
    onClickAppearance: () -> Unit,
    onClickAddNewTask: () -> Unit,
    onClickTask: (taskId: Int) -> Unit
) {
    composable<MainScreenDestination>(
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
                MainScreenDrawer(
                    drawerState = drawerState,
                    onClickAppearance = onClickAppearance,
                    onClickTaskLists = onClickTaskLists
                )
            }
        ) {
            MainScreen(
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
