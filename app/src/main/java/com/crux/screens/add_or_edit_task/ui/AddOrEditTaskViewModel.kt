package com.crux.screens.add_or_edit_task.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.crux.domain.model.Task
import com.crux.screens.add_or_edit_task.domain.repository.AddOrEditTaskRepository
import com.crux.ui.model.toUi
import com.crux.util.ALL_TASK_LISTS_ID
import com.crux.util.DEFAULT_TASK_LIST_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddOrEditTaskViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AddOrEditTaskRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<AddOrEditTaskScreenDestination>()

    private val _uiState = MutableStateFlow(AddOrEditTaskScreenState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<AddOrEditTaskScreenSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (args.taskId != null) {
                getTask(args.taskId)
            } else {
                val selectedTaskListId = repository.getSelectedTaskListIdFlow().first()
                _uiState.update {
                    it.copy(
                        selectedTaskListId = if (selectedTaskListId == ALL_TASK_LISTS_ID) {
                            DEFAULT_TASK_LIST_ID
                        } else {
                            selectedTaskListId
                        }
                    )
                }
            }
        }
        collectTaskLists()
    }

    fun onEvent(event: AddOrEditTaskScreenEvent) {
        when (event) {
            is AddOrEditTaskScreenEvent.OnValueChange -> {
                _uiState.update {
                    it.copy(
                        isTextFieldIncorrect = false,
                        textFieldValue = event.value
                    )
                }
            }
            AddOrEditTaskScreenEvent.OnClickSave -> {
                onClickSave()
            }
            is AddOrEditTaskScreenEvent.OnClickDelete -> {
                _uiState.update {
                    it.copy(isDeleteTaskDialogVisible = true)
                }
            }
            AddOrEditTaskScreenEvent.OnDismissDeleteConfirmation -> {
                _uiState.update {
                    it.copy(isDeleteTaskDialogVisible = false)
                }
            }
            is AddOrEditTaskScreenEvent.OnConfirmDeleteConfirmation -> {
                args.taskId?.let { taskId ->
                    deleteTaskById(id = taskId)
                }
            }
            is DueDateEvent -> {
                handleDueDateEvent(event = event)
            }
            is TaskListEvent -> {
                handleTaskListEvent(event = event)
            }
        }
    }

    private fun handleDueDateEvent(event: DueDateEvent) {
        when (event) {
            DueDateEvent.OnClickDueDate -> {
                _uiState.update {
                    it.copy(isDatePickerDialogVisible = true)
                }
            }
            DueDateEvent.OnDismissDatePicker -> {
                _uiState.update {
                    it.copy(isDatePickerDialogVisible = false)
                }
            }
            DueDateEvent.OnClickRemoveDueDate -> {
                _uiState.update {
                    it.copy(dueDate = null)
                }
            }
            is DueDateEvent.OnSelectDueDate -> {
                _uiState.update {
                    it.copy(dueDate = event.date)
                }
            }
            DueDateEvent.OnClickTime -> {
                _uiState.update {
                    it.copy(isTimePickerDialogVisible = true)
                }
            }
            DueDateEvent.OnDismissTimePicker -> {
                _uiState.update {
                    it.copy(isTimePickerDialogVisible = false)
                }
            }
        }
    }

    private fun handleTaskListEvent(event: TaskListEvent) {
        when (event) {
            is TaskListEvent.OnAddTextFieldValueChange -> {
                _uiState.update {
                    it.copy(addTextFieldValue = event.value)
                }
            }
            TaskListEvent.OnClickAddTaskList -> {
                _uiState.update {
                    it.copy(isAddTaskListDialogVisible = true)
                }
            }
            TaskListEvent.OnClickConfirmAddTaskList -> {
                onClickConfirmAddTaskList()
            }
            TaskListEvent.OnClickDismissAddTaskListDialog -> {
                _uiState.update {
                    it.copy(isAddTaskListDialogVisible = false)
                }
            }
            is TaskListEvent.OnSelectTaskList -> {
                _uiState.update {
                    it.copy(selectedTaskListId = event.id)
                }
            }
        }
    }

    private suspend fun getTask(id: Int) {
        val task = repository.getTaskById(id)?.toUi()

        if (task != null) {
            _uiState.update {
                it.copy(
                    task = task,
                    textFieldValue = task.title,
                    selectedTaskListId = task.listId,
                    dueDate = task.dueDateTime
                )
            }
        }
    }

    private fun collectTaskLists() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTaskLists().collect { taskLists ->
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

    private fun onClickSave() {
        viewModelScope.launch {
            val title = _uiState.value.textFieldValue

            if (title.isBlank()) {
                _uiState.update {
                    it.copy(isTextFieldIncorrect = true)
                }
                _sideEffects.send(AddOrEditTaskScreenSideEffect.TextFieldEmpty)
                return@launch
            }

            val task = _uiState.value.task

            if (task != null) {
                repository.updateTask(
                    task = task
                        .copy(
                            title = title.trim(),
                            listId = _uiState.value.selectedTaskListId,
                            dueDateTime = _uiState.value.dueDate
                        ).toDomain()
                )
            } else {
                repository.insertTask(
                    Task(
                        id = 0, // treated as not set
                        isCompleted = false,
                        title = title.trim(),
                        createdAt = System.currentTimeMillis(),
                        listId = _uiState.value.selectedTaskListId,
                        dueDateTime = _uiState.value.dueDate
                    )
                )
            }

            _sideEffects.send(AddOrEditTaskScreenSideEffect.TaskSaved)
        }
    }

    private fun deleteTaskById(id: Int) {
        viewModelScope.launch {
            repository.deleteTaskById(id = id)

            _uiState.update {
                it.copy(isDeleteTaskDialogVisible = false)
            }

            _sideEffects.send(AddOrEditTaskScreenSideEffect.TaskDeleted)
        }
    }

    private fun onClickConfirmAddTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            val newTaskListId = repository.addTaskList(
                name = _uiState.value.addTextFieldValue.trim()
            )

            _uiState.update {
                it.copy(
                    selectedTaskListId = newTaskListId.toInt(),
                    addTextFieldValue = "",
                    isAddTaskListDialogVisible = false
                )
            }
        }
    }
}
