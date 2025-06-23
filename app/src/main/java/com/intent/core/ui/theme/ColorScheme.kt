package com.intent.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LightColorScheme = lightColorScheme(
    background = GeistLightBackground,

    surface = GeistLightBackgroundSecondary,
    surfaceContainer = GeistLightGray1,
    surfaceVariant = GeistLightGray2,
    surfaceContainerHigh = GeistLightGray3,
    surfaceContainerHighest = GeistLightGray4,

    primary = GeistLightGray10,
    onPrimary = Color.White,

    outlineVariant = GeistLightGray5
)

internal val DarkColorScheme = darkColorScheme(
    background = GeistDarkBackground,

    surface = GeistDarkBackgroundSecondary,
    surfaceContainer = GeistDarkGray1,
    surfaceVariant = GeistDarkGray2,
    surfaceContainerHigh = GeistDarkGray3,
    surfaceContainerHighest = GeistDarkGray4,

    primary = GeistDarkGray10,
    onPrimary = Color.Black,
    outlineVariant = GeistDarkGray5
)

data class CustomColorScheme(
    val errorIndicator: Color
)

val LightCustomColorScheme = CustomColorScheme(
    errorIndicator = ErrorIndicatorColor
)

val DarkCustomColorScheme = CustomColorScheme(
    errorIndicator = ErrorIndicatorColorDark
)

val LocalCustomColors = staticCompositionLocalOf { LightCustomColorScheme }
