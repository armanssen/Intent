package com.crux.screens.task_lists.data.repository

import com.crux.data.database.AppDatabase
import com.crux.data.database.model.TaskListEntity
import com.crux.domain.model.TaskListWithCount
import com.crux.screens.task_lists.domain.repository.TaskListsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskListsRepositoryImpl
@Inject constructor(
    private val database: AppDatabase
) : TaskListsRepository {

    override fun getAllTaskListsFlow(): Flow<List<TaskListWithCount>> {
        return database
            .taskListEntityDao()
            .getAllFlowWithTaskCount()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override suspend fun updateTaskListName(id: Int, name: String) {
        database
            .taskListEntityDao()
            .updateName(
                id = id,
                name = name
            )
    }

    override suspend fun addTaskList(name: String) {
        database
            .taskListEntityDao()
            .insert(
                taskList = TaskListEntity(
                    name = name
                )
            )
    }

    override suspend fun deleteTaskListById(id: Int) {
        database
            .taskListEntityDao()
            .deleteById(
                id = id
            )
    }
}
