package com.crux.core.ui.model

import com.crux.core.domain.model.TaskList

data class TaskListUi(
    val id: Int,
    val name: String
) {

    fun toDomain() = TaskList(
        id = id,
        name = name
    )
}

fun TaskList.toUi() = TaskListUi(
    id = id,
    name = name
)
