package com.crux.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object CruxTheme {

    val colorScheme: CustomColorScheme
        @Composable @ReadOnlyComposable get() = LocalCustomColors.current
}
