package com.crux.screens.add_or_edit_task.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crux.R
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskDatePickerDialogView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskDueDateView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskFloatingActionButtonView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskTaskListSelectionView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskTextFieldView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskTopAppBarView
import com.crux.ui.component.AddOrEditTaskListDialogView
import com.crux.util.LaunchAndRepeatWithLifecycle
import com.crux.util.requestFocusWithDelay
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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val datePickerState = rememberDatePickerState()

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
                AddOrEditTaskScreenSideEffect.TaskDeleted -> {
                    onClickBack()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            AddOrEditTaskTopAppBarView(
                task = uiState.task,
                onClickBack = onClickBack,
                onClickDelete = { id ->
                    onEvent(AddOrEditTaskScreenEvent.OnClickDelete(id))
                }
            )
        },
        floatingActionButton = {
            AddOrEditTaskFloatingActionButtonView(
                onClickSave = {
                    onEvent(AddOrEditTaskScreenEvent.OnClickSave)
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Spacer(Modifier.height(8.dp))
            AddOrEditTaskTextFieldView(
                textFieldValue = uiState.textFieldValue,
                onValueChange = {
                    onEvent(AddOrEditTaskScreenEvent.OnValueChange(it))
                },
                isTextFieldIncorrect = uiState.isTextFieldIncorrect,
                focusRequester = focusRequester
            )
            Spacer(Modifier.height(36.dp))
            AddOrEditTaskDueDateView(
                onClick = {
                    onEvent(AddOrEditTaskScreenEvent.OnClickDueDate)
                }
            )
            AddOrEditTaskTaskListSelectionView(
                selectedTaskListId = uiState.selectedTaskListId,
                taskLists = uiState.taskLists,
                onSelectTaskList = {
                    onEvent(AddOrEditTaskScreenEvent.OnSelectTaskList(it))
                },
                onClickAdd = {
                    onEvent(AddOrEditTaskScreenEvent.OnClickAddTaskList)
                }
            )
        }
    }

    if (uiState.isAddTaskListDialogVisible) {
        AddOrEditTaskListDialogView(
            icon = Icons.Outlined.Add,
            title = stringResource(R.string.task_lists_add_task_list_title),
            textFieldValue = uiState.addTextFieldValue,
            onValueChange = {
                onEvent(AddOrEditTaskScreenEvent.OnAddTextFieldValueChange(it))
            },
            onDismissRequest = {
                onEvent(AddOrEditTaskScreenEvent.OnClickDismissAddTaskListDialog)
            },
            onConfirmation = {
                onEvent(AddOrEditTaskScreenEvent.OnClickConfirmAddTaskList)
            }
        )
    }

    if (uiState.isDatePickerDialogVisible) {
        AddOrEditTaskDatePickerDialogView(
            datePickerState = datePickerState,
            onDismiss = {
                onEvent(AddOrEditTaskScreenEvent.OnDismissDatePicker)
            },
            onDateSelected = {

            }
        )
    }
}
