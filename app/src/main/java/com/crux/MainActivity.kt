package com.crux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.crux.ui.add_or_edit_task.ui.AddOrEditTaskScreenDestination
import com.crux.ui.add_or_edit_task.ui.addOrEditTaskScreen
import com.crux.ui.main.ui.MainScreenDestination
import com.crux.ui.main.ui.mainScreen
import com.crux.ui.theme.CruxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CruxTheme {
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
                        }
                    )
                    addOrEditTaskScreen(
                        onClickBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}
