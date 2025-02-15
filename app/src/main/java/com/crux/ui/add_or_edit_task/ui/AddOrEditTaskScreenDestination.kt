package com.crux.ui.add_or_edit_task.ui

import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class AddOrEditTaskScreenDestination(
    val taskId: Int? = null
)

fun NavGraphBuilder.addOrEditTaskScreen() {
    composable<AddOrEditTaskScreenDestination>(
        enterTransition = {
            slideInHorizontally()
        }
    ) {
        AddOrEditTaskScreen(

        )
    }
}
