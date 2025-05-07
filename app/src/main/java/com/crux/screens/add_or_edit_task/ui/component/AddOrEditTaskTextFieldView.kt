package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.crux.R

@Composable
internal fun AddOrEditTaskTextFieldView(
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    isTextFieldIncorrect: Boolean,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textFieldValue,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = "Enter task"
            )
        },
        modifier = modifier
            .padding(horizontal = 12.dp)
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        supportingText = {
            if (isTextFieldIncorrect) {
                Text(stringResource(R.string.add_or_edit_task_screen_empty_title))
            }
        },
        isError = isTextFieldIncorrect
    )
}
