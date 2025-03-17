package com.crux.screens.appearance.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crux.R
import com.crux.domain.model.AppTheme

@Composable
internal fun AppearanceChooseThemeView(
    selectedAppTheme: AppTheme,
    onSelectAppTheme: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.appearance_screen_choose_title),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(4.dp))
        ThemeItemView(
            title = stringResource(R.string.appearance_screen_light_theme_title),
            isSelected = selectedAppTheme == AppTheme.LIGHT,
            onClick = {
                onSelectAppTheme(AppTheme.LIGHT)
            }
        )
        ThemeItemView(
            title = stringResource(R.string.appearance_screen_dark_theme_title),
            isSelected = selectedAppTheme == AppTheme.DARK,
            onClick = {
                onSelectAppTheme(AppTheme.DARK)
            }
        )
        ThemeItemView(
            title = stringResource(R.string.appearance_screen_system_default_title),
            isSelected = selectedAppTheme == AppTheme.SYSTEM_DEFAULT,
            onClick = {
                onSelectAppTheme(AppTheme.SYSTEM_DEFAULT)
            }
        )
    }
}

@Composable
private fun ThemeItemView(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = title
        )
    }
}

