package com.intent.screens.add_or_edit_task.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.intent.R
import com.intent.screens.add_or_edit_task.ui.component.date_picker.DatePickerDialogView
import com.intent.screens.add_or_edit_task.ui.component.date_picker.MenuItemDueDate
import com.intent.screens.add_or_edit_task.ui.component.AddOrEditTaskFloatingActionButtonView
import com.intent.screens.add_or_edit_task.ui.component.task_list_picker.MenuItemSelectTaskList
import com.intent.screens.add_or_edit_task.ui.component.AddOrEditTaskTextFieldView
import com.intent.screens.add_or_edit_task.ui.component.TopAppBarView
import com.intent.screens.add_or_edit_task.ui.component.DeleteTaskDialogView
import com.intent.screens.add_or_edit_task.ui.component.time_picker.TimePickerDialogView
import com.intent.screens.add_or_edit_task.ui.component.time_picker.MenuItemTime
import com.intent.screens.add_or_edit_task.ui.component.MenuItemMarkAsCompleted
import com.intent.core.ui.component.AddOrEditTaskListDialogView
import com.intent.util.LaunchAndRepeatWithLifecycle
import com.intent.util.requestFocusWithDelay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddOrEditTaskScreen(
    args: AddOrEditTaskScreenDestination,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    viewModel: AddOrEditTaskViewModel = hiltViewModel(),
    uiState: AddOrEditTaskScreenState = viewModel.uiState.collectAsStateWithLifecycle().value,
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
            TopAppBarView(
                isEditTask = args.taskId != null,
                scrollBehavior = scrollBehavior,
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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            Spacer(Modifier.height(8.dp))
            AddOrEditTaskTextFieldView(
                textFieldValue = uiState.textFieldValue,
                onValueChange = {
                    onEvent(AddOrEditTaskScreenEvent.OnValueChange(it))
                },
                isTextFieldIncorrect = uiState.isTextFieldIncorrect,
                focusRequester = focusRequester
            )
            if (uiState.task != null) {
                MenuItemMarkAsCompleted(
                    isChecked = uiState.isCompleted,
                    onCheckedChange = {
                        onEvent(AddOrEditTaskScreenEvent.OnCheckedChange(it))
                    }
                )
            } else {
                Spacer(Modifier.height(36.dp))
            }
            MenuItemDueDate(
                dueDate = uiState.dueDate,
                isNewTask = args.taskId == null,
                onClick = {
                    onEvent(DueDateEvent.OnClickDueDate)
                },
                onClickRemove = {
                    onEvent(DueDateEvent.OnClickRemoveDueDate)
                },
                onDateSelect = { selectedDate ->
                    onEvent(DueDateEvent.OnSelectDueDate(selectedDate))
                }
            )
            if (uiState.dueDate != null) {
                MenuItemTime(
                    dueDate = uiState.dueDate,
                    onClick = {
                        onEvent(DueDateEvent.OnClickTime)
                    },
                    onClickRemove = {
                        onEvent(DueDateEvent.OnClickRemoveTime)
                    }
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
            )
            MenuItemSelectTaskList(
                selectedTaskListId = uiState.selectedTaskListId,
                taskLists = uiState.taskLists,
                onSelectTaskList = {
                    onEvent(TaskListEvent.OnSelectTaskList(it))
                },
                onClickAdd = {
                    onEvent(TaskListEvent.OnClickAddTaskList)
                }
            )
            Spacer(Modifier.height(96.dp))
        }
    }

    if (uiState.isAddTaskListDialogVisible) {
        AddOrEditTaskListDialogView(
            icon = Icons.Rounded.Add,
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
        DatePickerDialogView(
            dueDate = uiState.dueDate,
            onDismiss = {
                onEvent(DueDateEvent.OnDismissDatePicker)
            },
            onDateSelect = { selectedDate ->
                onEvent(DueDateEvent.OnSelectDueDate(selectedDate))
            }
        )
    }

    if (uiState.isTimePickerDialogVisible) {
        TimePickerDialogView(
            dueDate = uiState.dueDate,
            onConfirm = { hour, minute ->
                onEvent(DueDateEvent.OnSelectTime(hour, minute))
            },
            onDismiss = {
                onEvent(DueDateEvent.OnDismissTimePicker)
            }
        )
    }

    if (uiState.isDeleteTaskDialogVisible) {
        DeleteTaskDialogView(
            onDismissRequest = {
                onEvent(AddOrEditTaskScreenEvent.OnDismissDeleteConfirmation)
            },
            onClickConfirm = {
                onEvent(AddOrEditTaskScreenEvent.OnConfirmDeleteConfirmation)
            }
        )
    }
}
