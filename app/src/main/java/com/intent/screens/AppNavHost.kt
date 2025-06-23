package com.intent.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.intent.screens.about.aboutScreen
import com.intent.screens.add_or_edit_task.ui.addOrEditTaskScreen
import com.intent.screens.appearance.ui.appearanceScreen
import com.intent.screens.completed_tasks.ui.completedTasksScreen
import com.intent.screens.home.ui.HomeScreenDestination
import com.intent.screens.home.ui.homeScreen
import com.intent.screens.settings.ui.settingsScreen
import com.intent.screens.task_lists.ui.taskListsScreen

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
