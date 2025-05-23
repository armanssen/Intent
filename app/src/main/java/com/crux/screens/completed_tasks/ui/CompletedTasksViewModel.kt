package com.crux.screens.completed_tasks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.screens.completed_tasks.domain.repository.CompletedTasksRepository
import com.crux.core.ui.model.TaskUi
import com.crux.core.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CompletedTasksViewModel
@Inject constructor(
    private val repository: CompletedTasksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompletedTasksScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        collectCompletedTasks()
    }

    fun onEvent(event: CompletedTasksScreenEvent) {
    }

    private fun collectCompletedTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCompletedTasksFlow()
                .collect { tasks ->
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
} 