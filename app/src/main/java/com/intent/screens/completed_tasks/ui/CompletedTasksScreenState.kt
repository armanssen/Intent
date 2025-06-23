package com.intent.screens.completed_tasks.ui

import com.intent.core.ui.model.TaskUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CompletedTasksScreenState(
    val tasks: ImmutableList<TaskUi> = persistentListOf()
)
