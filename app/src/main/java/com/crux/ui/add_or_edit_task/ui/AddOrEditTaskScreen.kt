package com.crux.ui.add_or_edit_task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crux.util.LaunchAndRepeatWithLifecycle
import com.crux.util.requestFocusWithDelay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    viewModel: AddOrEditTaskViewModel = hiltViewModel(),
    uiState: AddOrEditTaskScreenState = viewModel.uiState.collectAsState().value,
    onEvent: (AddOrEditTaskScreenEvent) -> Unit = viewModel::onEvent
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocusWithDelay()
    }

    LaunchAndRepeatWithLifecycle {
        viewModel.sideEffects.collectLatest { sideEffect ->
            when (sideEffect) {
                AddOrEditTaskScreenSideEffect.TaskSaved -> {
                    onClickBack()
                }
                AddOrEditTaskScreenSideEffect.TextFieldEmpty -> {
                    focusManager.clearFocus(force = true) // Clear focus first
                    focusRequester.requestFocus()
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFfcf8f5))
    ) {
        Column {
            TopAppBar(
                windowInsets = WindowInsets(0.dp),
                title = {
                    if (uiState.task == null) {
                        Text(
                            text = "New Task"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onClickBack,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "arrow back"
                            )
                        }
                    )
                }
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = uiState.textFieldValue,
                    onValueChange = {
                        onEvent(AddOrEditTaskScreenEvent.OnValueChange(it))
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    supportingText = {
                        if (uiState.isTextFieldIncorrect) {
                            Text("Title is Empty")
                        }
                    },
                    isError = uiState.isTextFieldIncorrect
                )
            }
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                onEvent(AddOrEditTaskScreenEvent.OnClickSave)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "done icon"
                )
            },
            text = {
                Text(
                    text = "Save task"
                )
            }
        )
    }
}
