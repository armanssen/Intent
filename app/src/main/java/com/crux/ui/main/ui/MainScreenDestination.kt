package com.crux.ui.main.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object MainScreenDestination

fun NavGraphBuilder.mainScreen(
    onClickAddNewTask: () -> Unit
) {
    composable<MainScreenDestination> {
        MainScreen(
            onClickAddNewTask = onClickAddNewTask
        )
    }
}
