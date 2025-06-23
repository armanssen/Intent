package com.intent.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object IntentTheme {

    val colorScheme: CustomColorScheme
        @Composable @ReadOnlyComposable get() = LocalCustomColors.current
}
