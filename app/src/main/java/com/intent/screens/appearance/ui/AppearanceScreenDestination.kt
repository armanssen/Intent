package com.intent.screens.appearance.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AppearanceScreenDestination

fun NavGraphBuilder.appearanceScreen(
    onClickBack: () -> Unit
) {
    composable<AppearanceScreenDestination>(
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
        AppearanceScreen(
            onClickBack = onClickBack
        )
    }
}
