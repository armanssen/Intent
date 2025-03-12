package com.crux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.crux.ui.add_or_edit_task.ui.AddOrEditTaskScreenDestination
import com.crux.ui.add_or_edit_task.ui.addOrEditTaskScreen
import com.crux.ui.appearance.ui.AppearanceScreenDestination
import com.crux.ui.appearance.ui.appearanceScreen
import com.crux.ui.main.ui.MainScreenDestination
import com.crux.ui.main.ui.mainScreen
import com.crux.ui.theme.CruxTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(1000)
            keepSplashScreen = false
        }
        enableEdgeToEdge()

        setContent {
            CruxTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = MainScreenDestination
                    ) {
                        mainScreen(
                            onClickAddNewTask = {
                                navController.navigate(
                                    AddOrEditTaskScreenDestination()
                                )
                            },
                            onClickTask = { taskId ->
                                navController.navigate(
                                    AddOrEditTaskScreenDestination(taskId = taskId)
                                )
                            },
                            onClickAppearance = {
                                navController.navigate(
                                    AppearanceScreenDestination
                                )
                            }
                        )
                        addOrEditTaskScreen(
                            onClickBack = {
                                navController.navigateUp()
                            }
                        )
                        appearanceScreen(
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
