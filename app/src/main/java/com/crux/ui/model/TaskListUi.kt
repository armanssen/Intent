package com.crux.ui.model

import com.crux.domain.model.TaskList

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
