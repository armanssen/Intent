package com.intent.screens.home.ui

import com.intent.core.data.datastore.PreferenceDefaultValues
import com.intent.core.ui.model.TaskListWithCountUi
import com.intent.core.ui.model.TaskUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeScreenState(
    val selectedTaskListId: Int = PreferenceDefaultValues.SELECTED_TASK_LIST_ID,
    val isHideCompletedTasksEnabled: Boolean = false,
    val tasks: ImmutableList<TaskUi> = persistentListOf(),
    val taskLists: ImmutableList<TaskListWithCountUi> = persistentListOf()
)
