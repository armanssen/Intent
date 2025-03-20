package com.crux.screens.add_or_edit_task.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crux.R
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskFloatingActionButtonView
import com.crux.screens.add_or_edit_task.ui.component.AddOrEditTaskTaskListSelectionView
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
            TextField(
                value = TextFieldValue(
                    text = uiState.textFieldValue,
                    selection = TextRange(uiState.textFieldValue.length)
                ),
                onValueChange = {
                    onEvent(AddOrEditTaskScreenEvent.OnValueChange(it.text))
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (uiState.isTextFieldIncorrect) {
                        Text(stringResource(R.string.add_or_edit_task_screen_empty_title))
                    }
                },
                isError = uiState.isTextFieldIncorrect
            )
            Spacer(Modifier.height(36.dp))
            Row(
                modifier = Modifier
                    .clickable {

                    }
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "calendar icon"
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.add_or_edit_task_screen_due_date)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AddOrEditTaskTaskListSelectionView(
                    selectedTaskListId = uiState.selectedTaskListId,
                    taskLists = uiState.taskLists,
                    onSelectTaskList = {
                        onEvent(AddOrEditTaskScreenEvent.OnSelectTaskList(it))
                    },
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        onEvent(AddOrEditTaskScreenEvent.OnClickAddTaskList)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add icon"
                    )
                }
            }
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
}
