package com.crux.ui.add_or_edit_task.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        AddOrEditTaskScreen(
            onClickBack = onClickBack
        )
    }
}
