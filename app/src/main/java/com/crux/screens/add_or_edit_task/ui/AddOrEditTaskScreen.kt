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
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskDeleteTaskDialogView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskTimePickerDialogView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskTimeView
import com.crux.ui.component.AddOrEditTaskListDialogView
import com.crux.util.LaunchAndRepeatWithLifecycle
import com.crux.util.requestFocusWithDelay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskScreen(
    args: AddOrEditTaskScreenDestination,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    viewModel: AddOrEditTaskViewModel = hiltViewModel(),
    uiState: AddOrEditTaskScreenState = viewModel.uiState.collectAsState().value,
    onEvent: (AddOrEditTaskScreenEvent) -> Unit = viewModel::onEvent
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        if (args.taskId == null) {
            focusRequester.requestFocusWithDelay()
        }
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
                isEditTask = args.taskId != null,
                onClickBack = onClickBack,
                onClickDelete = {
                    onEvent(AddOrEditTaskScreenEvent.OnClickDelete)
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
                dueDate = uiState.dueDate,
                onClick = {
                    onEvent(DueDateEvent.OnClickDueDate)
                },
                onClickRemove = {
                    onEvent(DueDateEvent.OnClickRemoveDueDate)
                }
            )
            if (uiState.dueDate != null) {
                AddOrEditTaskTimeView(
                    onClick = {
                        onEvent(DueDateEvent.OnClickTime)
                    }
                )
            }
            AddOrEditTaskTaskListSelectionView(
                selectedTaskListId = uiState.selectedTaskListId,
                taskLists = uiState.taskLists,
                onSelectTaskList = {
                    onEvent(TaskListEvent.OnSelectTaskList(it))
                },
                onClickAdd = {
                    onEvent(TaskListEvent.OnClickAddTaskList)
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
                onEvent(TaskListEvent.OnAddTextFieldValueChange(it))
            },
            onDismissRequest = {
                onEvent(TaskListEvent.OnClickDismissAddTaskListDialog)
            },
            onConfirmation = {
                onEvent(TaskListEvent.OnClickConfirmAddTaskList)
            }
        )
    }

    if (uiState.isDatePickerDialogVisible) {
        AddOrEditTaskDatePickerDialogView(
            dueDate = uiState.dueDate,
            onDismiss = {
                onEvent(DueDateEvent.OnDismissDatePicker)
            },
            onDateSelected = { selectedDate ->
                onEvent(DueDateEvent.OnSelectDueDate(selectedDate))
            }
        )
    }

    if (uiState.isTimePickerDialogVisible) {
        AddOrEditTaskTimePickerDialogView(
            onConfirm = {},
            onDismiss = {
                onEvent(DueDateEvent.OnDismissTimePicker)
            }
        )
    }

    if (uiState.isDeleteTaskDialogVisible) {
        AddOrEditTaskDeleteTaskDialogView(
            onDismissRequest = {
                onEvent(AddOrEditTaskScreenEvent.OnDismissDeleteConfirmation)
            },
            onClickConfirm = {
                onEvent(AddOrEditTaskScreenEvent.OnConfirmDeleteConfirmation)
            }
        )
    }
}
