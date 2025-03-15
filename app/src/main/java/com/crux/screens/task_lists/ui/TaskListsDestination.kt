package com.crux.screens.task_lists.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object TaskListsDestination

fun NavGraphBuilder.taskListsScreen(
    onClickBack: () -> Unit
) {
    composable<TaskListsDestination>(
        enterTransition = {
            slideInHorizontally { it / 2 }
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = 300)
            )
        }
    ) {
        TaskListsScreen(
            onClickBack = onClickBack
        )
    }
}