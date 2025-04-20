package com.crux.screens

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.crux.domain.model.AppTheme
import com.crux.screens.add_or_edit_task.ui.AddOrEditTaskScreenDestination
import com.crux.screens.add_or_edit_task.ui.addOrEditTaskScreen
import com.crux.screens.appearance.ui.AppearanceScreenDestination
import com.crux.screens.appearance.ui.appearanceScreen
import com.crux.screens.about.AboutScreenDestination
import com.crux.screens.about.aboutScreen
import com.crux.screens.completed_tasks.ui.CompletedTasksScreenDestination
import com.crux.screens.completed_tasks.ui.completedTasksScreen
import com.crux.screens.home.ui.HomeScreenDestination
import com.crux.screens.home.ui.homeScreen
import com.crux.screens.task_lists.ui.TaskListsDestination
import com.crux.screens.task_lists.ui.taskListsScreen
import com.crux.ui.theme.CruxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashscreen.setKeepOnScreenCondition {
            viewModel.uiState.value.isSplashScreenVisible
        }

        setContent {
            val activity = LocalActivity.current as ComponentActivity
            val uiState = viewModel.uiState.collectAsState()
            val uiMode = LocalConfiguration.current.uiMode

            val isDarkThemeEnabled = remember(uiState.value.selectedAppTheme) {
                uiState.value.selectedAppTheme == AppTheme.DARK || (
                        uiState.value.selectedAppTheme == AppTheme.SYSTEM_DEFAULT &&
                                (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
                        )
            }

            LaunchedEffect(isDarkThemeEnabled) {
                activity.enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                        detectDarkMode = {
                            isDarkThemeEnabled
                        }
                    ),
                    navigationBarStyle =  SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                        detectDarkMode = {
                            isDarkThemeEnabled
                        }
                    )
                )
            }

            CruxTheme(
                appTheme = uiState.value.selectedAppTheme,
                isDynamicColorEnabled = uiState.value.isDynamicColorEnabled
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenDestination
                    ) {
                        homeScreen(
                            onClickAddNewTask = {
                                navController.navigate(
                                    AddOrEditTaskScreenDestination()
                                )
                            },
                            onClickTask = { taskId ->
                                navController.navigate(
                                    route = AddOrEditTaskScreenDestination(taskId = taskId),
                                    navOptions = NavOptions.Builder()
                                        .setLaunchSingleTop(true)
                                        .build()
                                )
                            },
                            onClickTaskLists = {
                                navController.navigate(TaskListsDestination)
                            },
                            onClickAppearance = {
                                navController.navigate(AppearanceScreenDestination)
                            },
                            onClickAbout = {
                                navController.navigate(AboutScreenDestination)
                            },
                            onClickCompletedTasks = {
                                navController.navigate(CompletedTasksScreenDestination)
                            }
                        )
                        addOrEditTaskScreen(
                            onClickBack = {
                                navController.navigateUp()
                            }
                        )
                        taskListsScreen(
                            onClickBack = {
                                navController.navigateUp()
                            }
                        )
                        completedTasksScreen(
                            onClickBack = {
                                navController.navigateUp()
                            }
                        )
                        appearanceScreen(
                            onClickBack = {
                                navController.navigateUp()
                            }
                        )
                        aboutScreen(
                            onClickBack = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}
