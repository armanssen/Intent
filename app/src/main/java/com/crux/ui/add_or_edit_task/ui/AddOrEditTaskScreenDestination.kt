package com.crux.ui.add_or_edit_task.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
            slideInHorizontally(
                initialOffsetX = {
                    it / 2
                }
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = {
                    it
                }
            )
        }
    ) {
        AddOrEditTaskScreen(
            onClickBack = onClickBack
        )
    }
}
