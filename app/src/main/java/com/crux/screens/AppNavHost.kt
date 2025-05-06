package com.crux.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.crux.screens.about.aboutScreen
import com.crux.screens.add_or_edit_task.ui.addOrEditTaskScreen
import com.crux.screens.appearance.ui.appearanceScreen
import com.crux.screens.completed_tasks.ui.completedTasksScreen
import com.crux.screens.home.ui.HomeScreenDestination
import com.crux.screens.home.ui.homeScreen
import com.crux.screens.settings.ui.settingsScreen
import com.crux.screens.task_lists.ui.taskListsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination
    ) {
        homeScreen(
            navController = navController
        )
        addOrEditTaskScreen(
            onClickBack = navController::navigateUp
        )
        taskListsScreen(
            onClickBack = navController::navigateUp
        )
        completedTasksScreen(
            onClickBack = navController::navigateUp
        )
        appearanceScreen(
            onClickBack = navController::navigateUp
        )
        aboutScreen(
            onClickBack = navController::navigateUp
        )
        settingsScreen(
            navigateBack = navController::navigateUp
        )
    }
}
