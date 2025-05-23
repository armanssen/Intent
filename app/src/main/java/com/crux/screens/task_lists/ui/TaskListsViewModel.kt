package com.crux.screens.task_lists.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.screens.task_lists.domain.repository.TaskListsRepository
import com.crux.core.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TaskListsViewModel
@Inject constructor(
    private val repository: TaskListsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListsScreenState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<TaskListsScreenSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    init {
        collectTaskLists()
    }

    fun onEvent(event: TaskListsScreenEvent) {
        when (event) {
            TaskListsScreenEvent.OnClickAddTaskList -> {
                _uiState.update {
                    it.copy(isAddTaskListDialogVisible = true)
                }
            }
            TaskListsScreenEvent.OnClickDismissAddTaskListDialog -> {
                _uiState.update {
                    it.copy(isAddTaskListDialogVisible = false)
                }
            }
            is TaskListsScreenEvent.OnAddTextFieldValueChange -> {
                _uiState.update {
                    it.copy(addTextFieldValue = event.value)
                }
            }
            TaskListsScreenEvent.OnClickConfirmAddTaskList -> {
                onClickConfirmAddTaskList()
            }
            is TaskListsScreenEvent.OnClickDelete -> {
                _uiState.update {
                    it.copy(taskListForDeletion = event.taskList)
                }
            }
            is TaskListsScreenEvent.OnConfirmDeleteConfirmation -> {
                onConfirmDeleteConfirmation(
                    taskListId = event.taskListId
                )
            }
            TaskListsScreenEvent.OnDismissDeleteConfirmation -> {
                _uiState.update {
                    it.copy(taskListForDeletion = null)
                }
            }
            is TaskListsScreenEvent.OnClickEdit -> {
                _uiState.update {
                    it.copy(
                        taskListForEdit = event.taskList,
                        editTextFieldValue = event.taskList.name
                    )
                }
            }
            TaskListsScreenEvent.OnClickDismissEditTaskListDialog -> {
                _uiState.update {
                    it.copy(taskListForEdit = null)
                }
            }
            is TaskListsScreenEvent.OnConfirmEditConfirmation -> {
                onConfirmEditConfirmation(
                    taskListId = event.taskListId
                )
            }
            is TaskListsScreenEvent.OnEditTextFieldValueChange -> {
                _uiState.update {
                    it.copy(editTextFieldValue = event.value)
                }
            }
            is TaskListsScreenEvent.OnClickTaskList -> {
                onClickTaskList(taskListId = event.taskList.id)
            }
        }
    }

    private fun collectTaskLists() {
        viewModelScope.launch(Dispatchers.IO) {
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

    private fun onClickConfirmAddTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTaskList(
                name = _uiState.value.addTextFieldValue.trim()
            )

            _uiState.update {
                it.copy(
                    addTextFieldValue = "",
                    isAddTaskListDialogVisible = false
                )
            }
        }
    }

    private fun onConfirmDeleteConfirmation(taskListId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTaskListById(id = taskListId)
            _uiState.update {
                it.copy(taskListForDeletion = null)
            }
        }
    }

    private fun onConfirmEditConfirmation(taskListId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTaskListName(
                id = taskListId,
                name = _uiState.value.editTextFieldValue.trim()
            )
            _uiState.update {
                it.copy(taskListForEdit = null)
            }
        }
    }

    private fun onClickTaskList(taskListId: Int) {
        viewModelScope.launch {
            repository.setSelectedTaskListId(taskListId = taskListId)
            _sideEffects.send(TaskListsScreenSideEffect.NavigateBack)
        }
    }
}
