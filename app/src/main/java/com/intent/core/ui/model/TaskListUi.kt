package com.intent.core.ui.model

import com.intent.core.domain.model.TaskList

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
