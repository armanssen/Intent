package com.intent.screens.add_or_edit_task.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class AddOrEditTaskScreenDestination(
    val taskId: Int? = null
)

fun NavGraphBuilder.addOrEditTaskScreen(
    onClickBack: () -> Unit,
) {
    composable<AddOrEditTaskScreenDestination>(
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
        val args = it.toRoute<AddOrEditTaskScreenDestination>()

        AddOrEditTaskScreen(
            args = args,
            onClickBack = onClickBack
        )
    }
}
