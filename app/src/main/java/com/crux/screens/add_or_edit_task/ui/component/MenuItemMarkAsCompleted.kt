package com.crux.screens.add_or_edit_task.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration

@Composable
internal fun MenuItemMarkAsCompleted(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onCheckedChange(!isChecked)
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors().copy(
                checkedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                checkedBoxColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedBoxColor = MaterialTheme.colorScheme.surfaceContainer
            )
        )
        Text(
            text = "Mark as completed",
            style = LocalTextStyle.current.let { style ->
                if (isChecked) {
                    style.copy(
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                } else {
                    style
                }
            }
        )
    }
}
