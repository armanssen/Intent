package com.crux.ui.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crux.ui.main.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getAllTasks()
        }
    }
}
