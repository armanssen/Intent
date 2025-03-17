package com.crux.screens.appearance.ui

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crux.R
import com.crux.screens.appearance.ui.component.AppearanceChooseThemeView
import com.crux.screens.appearance.ui.component.AppearanceDynamicColorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppearanceScreen(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppearanceViewModel = hiltViewModel(),
    uiState: AppearanceScreenState = viewModel.uiState.collectAsStateWithLifecycle().value,
    onEvent: (AppearanceScreenEvent) -> Unit = viewModel::onEvent
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.appearance_screen_title))
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
        Column(modifier = Modifier.padding(padding)) {
            Spacer(Modifier.height(12.dp))
            AppearanceChooseThemeView(
                selectedAppTheme = uiState.selectedAppTheme,
                onSelectAppTheme = {
                    onEvent(AppearanceScreenEvent.OnSelectAppTheme(it))
                }
            )
            Spacer(Modifier.height(8.dp))
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
