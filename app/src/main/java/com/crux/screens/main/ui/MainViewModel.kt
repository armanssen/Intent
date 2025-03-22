package com.crux.screens.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.screens.main.domain.repository.MainRepository
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
internal class MainViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        collectAllTasks()
        collectAllTaskLists()
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnCheckedChange -> {
                onCheckedChange(
                    task = event.task,
                    isChecked = event.isChecked
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
}
