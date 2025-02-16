package com.crux.ui.main.ui

import com.crux.ui.model.TaskUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainScreenState(
    val tasks: ImmutableList<TaskUi> = persistentListOf()
)
