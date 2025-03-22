package com.crux.screens.main.ui

import com.crux.data.datastore.PreferenceDefaultValues
import com.crux.ui.model.TaskListWithCountUi
import com.crux.ui.model.TaskUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainScreenState(
    val selectedTaskListId: Int = PreferenceDefaultValues.SELECTED_TASK_LIST_ID,
    val tasks: ImmutableList<TaskUi> = persistentListOf(),
    val taskLists: ImmutableList<TaskListWithCountUi> = persistentListOf()
)
