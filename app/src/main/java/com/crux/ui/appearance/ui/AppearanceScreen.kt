package com.crux.ui.appearance.ui

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.domain.model.AppTheme
import com.crux.ui.appearance.ui.component.AppearanceDynamicColorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppearanceScreen(
    modifier: Modifier = Modifier,
    viewModel: AppearanceViewModel = hiltViewModel(),
    uiState: AppearanceScreenState = viewModel.uiState.collectAsStateWithLifecycle().value,
    onEvent: (AppearanceScreenEvent) -> Unit = viewModel::onEvent,
    onClickBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Appearance")
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Choose theme",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(Modifier.height(4.dp))
                ThemeItemView(
                    title = "Light",
                    isSelected = uiState.selectedAppTheme == AppTheme.LIGHT,
                    onClick = {
                        onEvent(AppearanceScreenEvent.OnSelectAppTheme(AppTheme.LIGHT))
                    }
                )
                ThemeItemView(
                    title = "Dark",
                    isSelected = uiState.selectedAppTheme == AppTheme.DARK,
                    onClick = {
                        onEvent(AppearanceScreenEvent.OnSelectAppTheme(AppTheme.DARK))
                    }
                )
                ThemeItemView(
                    title = "System Default",
                    isSelected = uiState.selectedAppTheme == AppTheme.SYSTEM_DEFAULT,
                    onClick = {
                        onEvent(AppearanceScreenEvent.OnSelectAppTheme(AppTheme.SYSTEM_DEFAULT))
                    }
                )
            }
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    AppearanceDynamicColorView(
                        isDynamicColorEnabled = uiState.isDynamicColorEnabled,
                        onClick = { isChecked ->
                            onEvent(AppearanceScreenEvent.OnClickMaterialColors(isChecked = isChecked))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeItemView(
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
