package com.crux.screens.settings.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SettingsDestination

fun NavGraphBuilder.settingsScreen(
    navigateBack: () -> Unit
) {
    composable<SettingsDestination>(
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
        SettingsScreen(
            navigateBack = navigateBack
        )
    }
}
