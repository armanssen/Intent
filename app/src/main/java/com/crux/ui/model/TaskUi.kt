package com.crux.ui.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.crux.domain.model.Task
import kotlinx.serialization.Serializable

@Serializable
data class TaskUi(
    val id: Int,
    val title: String,
    val createdAt: Long,
    val isCompleted: Boolean,
    val listId: Int
) {

    fun toDomain() = Task(
        id = id,
        title = title,
        createdAt = createdAt,
        isCompleted = isCompleted,
        listId = listId
    )
}

fun Task.toUi() = TaskUi(
    id = id,
    title = title,
    createdAt = createdAt,
    isCompleted = isCompleted,
    listId = listId
)

class TaskPreviewParameterProvider : PreviewParameterProvider<TaskUi> {
    override val values = sequenceOf(
        TaskUi(
            id = 1,
            title = "Task title 1",
            createdAt = System.currentTimeMillis(),
            isCompleted = true,
            listId = 1
        ),
        TaskUi(
            id = 2,
            title = "Task title 2",
            createdAt = System.currentTimeMillis(),
            isCompleted = false,
            listId = 1
        ),
        TaskUi(
            id = 3,
            title = "Task title 3",
            createdAt = System.currentTimeMillis(),
            isCompleted = false,
            listId = 2
        )
    )
}
