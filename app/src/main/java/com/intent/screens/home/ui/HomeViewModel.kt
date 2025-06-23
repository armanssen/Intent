package com.intent.screens.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intent.screens.home.domain.repository.HomeRepository
import com.intent.core.ui.model.TaskUi
import com.intent.core.ui.model.toUi
import com.intent.util.ALL_TASK_LISTS_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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
        collectTasksAndFilter()
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
            HomeScreenEvent.OnClickHideCompletedTasks -> {
                onClickHideCompletedTasks()
            }
        }
    }

    private fun collectTasksAndFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                repository.getAllTasksFlow(),
                repository.getIsHideCompletedTasksEnabled(),
                repository.getSelectedTaskListIdFlow(),
                repository.getAllTaskListsFlow()
            ) { tasks, isHideCompletedTasksEnabled, selectedTaskListId, taskLists ->
                _uiState.value.copy(
                    isHideCompletedTasksEnabled = isHideCompletedTasksEnabled,
                    selectedTaskListId = selectedTaskListId,
                    tasks = tasks
                        .filter { task ->
                            selectedTaskListId == ALL_TASK_LISTS_ID || task.listId == selectedTaskListId
                        }
                        .filter { task ->
                            !isHideCompletedTasksEnabled || !task.isCompleted
                        }
                        .map { it.toUi() }
                        .toPersistentList(),
                    taskLists = taskLists
                        .map { taskList -> taskList.toUi() }
                        .toPersistentList()
                )
            }.collectLatest { newState ->
                _uiState.update { newState }
            }
        }
    }

    private fun onClickHideCompletedTasks() {
        viewModelScope.launch {
            repository.updateIsHideCompletedTasksEnabled(
                value = !_uiState.value.isHideCompletedTasksEnabled
            )
        }
    }

    private fun onCheckedChange(
        task: TaskUi,
        isChecked: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_uiState.value.isHideCompletedTasksEnabled) {
                delay(UPDATE_TASK_COMPLETION_DELAY)
            }
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

    companion object {
        private const val UPDATE_TASK_COMPLETION_DELAY = 300L
    }
}
