package com.crux.ui.main.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object MainScreenDestination

fun NavGraphBuilder.mainScreen(
    onClickAddNewTask: () -> Unit
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
        MainScreen(
            onClickAddNewTask = onClickAddNewTask
        )
    }
}
