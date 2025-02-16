package com.crux.ui.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.ui.main.domain.repository.MainRepository
import com.crux.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getAllTasks()
    }

    fun onEvent(event: MainScreenEvent) {

    }

    private fun getAllTasks() {
        viewModelScope.launch {
            val tasks = repository.getAllTasks().map {
                it.toUi()
            }

            _uiState.update {
                _uiState.value.copy(
                    tasks = tasks.toPersistentList()
                )
            }
        }
    }
}
