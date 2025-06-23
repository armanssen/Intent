package com.intent.screens.add_or_edit_task.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.intent.core.domain.model.Task
import com.intent.screens.add_or_edit_task.domain.repository.AddOrEditTaskRepository
import com.intent.core.ui.model.toUi
import com.intent.util.ALL_TASK_LISTS_ID
import com.intent.util.DEFAULT_TASK_LIST_ID
import com.intent.util.DateTimeUtils
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

    private suspend fun getTask(id: Int) {
        val task = repository.getTaskById(id)?.toUi() ?: return

        _uiState.update {
            it.copy(
                task = task,
                textFieldValue = task.title,
                isCompleted = task.isCompleted,
                selectedTaskListId = task.listId,
                dueDate = task.dueDateTime
            )
        }
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
            is AddOrEditTaskScreenEvent.OnCheckedChange -> {
                _uiState.update {
                    it.copy(isCompleted = event.value)
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
                onSelectDueDate(event.date)
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
            DueDateEvent.OnClickRemoveTime -> {
                val dueDate = _uiState.value.dueDate ?: return

                onSelectDueDate(dueDate)
            }
            is DueDateEvent.OnSelectTime -> {
                onSelectTime(
                    hour = event.hour,
                    minute = event.minute
                )
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
        viewModelScope.launch {
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

    private fun onSelectDueDate(date: Long) {
        val dueDate = DateTimeUtils
            .getDateWithTime(
                millis = date,
                hour = DateTimeUtils.ALL_DAY_HOUR,
                minute = DateTimeUtils.ALL_DAY_MINUTE,
                second = DateTimeUtils.ALL_DAY_SECOND
            )

        _uiState.update {
            it.copy(dueDate = dueDate)
        }
    }

    private fun onSelectTime(
        hour: Int,
        minute: Int
    ) {
        val dueDate = _uiState.value.dueDate ?: return

        val dueDateWithTime = DateTimeUtils.getDateWithTime(
            millis = dueDate,
            hour = hour,
            minute = minute
        )

        _uiState.update {
            it.copy(dueDate = dueDateWithTime)
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
                    task = task.copy(
                        title = title.trim(),
                        isCompleted = _uiState.value.isCompleted,
                        listId = _uiState.value.selectedTaskListId,
                        dueDateTime = _uiState.value.dueDate
                    ).toDomain()
                )
            } else {
                repository.insertTask(
                    Task(
                        id = 0, // treated as not set
                        title = title.trim(),
                        isCompleted = false,
                        listId = _uiState.value.selectedTaskListId,
                        dueDateTime = _uiState.value.dueDate,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }

            _sideEffects.send(AddOrEditTaskScreenSideEffect.TaskSaved)
        }
    }
}
