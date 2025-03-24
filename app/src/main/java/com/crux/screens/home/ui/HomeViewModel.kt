package com.crux.screens.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.screens.home.domain.repository.HomeRepository
import com.crux.ui.model.TaskUi
import com.crux.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel
@Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        collectAllTasks()
        collectAllTaskLists()
        collectSelectedTaskListId()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnCheckedChange -> {
                onCheckedChange(
                    task = event.task,
                    isChecked = event.isChecked
                )
            }
            is HomeScreenEvent.OnSelectTaskList -> {
                onSelectTaskList(
                    taskListId = event.taskListId
                )
            }
        }
    }

    private fun collectAllTasks() {
        viewModelScope.launch {
            repository.getAllTasksFlow()
                .collectLatest { tasks ->
                    _uiState.update {
                        it.copy(
                            tasks = tasks
                                .map { task -> task.toUi() }
                                .toPersistentList()
                        )
                    }
                }
        }
    }

    private fun collectAllTaskLists() {
        viewModelScope.launch {
            repository.getAllTaskListsFlow()
                .collectLatest { taskLists ->
                    _uiState.update {
                        it.copy(
                            taskLists = taskLists
                                .map { taskList -> taskList.toUi() }
                                .toPersistentList()
                        )
                    }
                }
        }
    }

    private fun collectSelectedTaskListId() {
        viewModelScope.launch {
            repository.getSelectedTaskListIdFlow()
                .collectLatest { taskListId ->
                    _uiState.update {
                        it.copy(selectedTaskListId = taskListId)
                    }
                }
        }
    }

    private fun onCheckedChange(
        task: TaskUi,
        isChecked: Boolean
    ) {
        viewModelScope.launch {
            repository.updateTaskCompletion(
                id = task.id,
                isCompleted = isChecked
            )
        }
    }

    private fun onSelectTaskList(taskListId: Int) {
        viewModelScope.launch {
            repository.setSelectedTaskListId(
                taskListId = taskListId
            )
        }
    }
}
