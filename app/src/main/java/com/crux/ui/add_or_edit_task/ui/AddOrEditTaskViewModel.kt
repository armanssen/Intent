package com.crux.ui.add_or_edit_task.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.crux.ui.add_or_edit_task.domain.repository.AddOrEditTaskRepository
import com.crux.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            args.taskId?.let { taskId ->
                getTask(taskId)
            }
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
        }
    }

    private suspend fun getTask(id: Int) {
        val task = repository.getTaskById(id)?.toUi()

        if (task != null) {
            _uiState.update {
                it.copy(
                    task = task,
                    textFieldValue = task.title
                )
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
                            title = title
                        ).toDomain()
                )
            } else {
                repository.insertTask(
                    title = title,
                    createdAt = System.currentTimeMillis()
                )
            }

            _sideEffects.send(AddOrEditTaskScreenSideEffect.TaskSaved)
        }
    }
}
