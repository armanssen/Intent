package com.crux.ui.main.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object MainScreenDestination

fun NavGraphBuilder.mainScreen() {
    composable<MainScreenDestination> {
        MainScreen()
    }
}
