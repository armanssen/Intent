package com.intent.screens.completed_tasks.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CompletedTasksScreenDestination

fun NavGraphBuilder.completedTasksScreen(
    onClickBack: () -> Unit
) {
    composable<CompletedTasksScreenDestination>(
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
        CompletedTasksScreen(
            onClickBack = onClickBack
        )
    }
}
