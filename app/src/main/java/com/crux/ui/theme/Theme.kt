package com.crux.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    background = GeistLightBackground,

    surface = GeistLightBackgroundSecondary,
    surfaceContainer = GeistLightGray1,
    surfaceVariant = GeistLightGray2,
    surfaceContainerHighest = GeistLightGray3,

    primary = GeistLightGray10,
    onPrimary = Color.White
)

private val DarkColorScheme = darkColorScheme(
    background = GeistDarkBackground,

    surface = GeistDarkBackgroundSecondary,
    surfaceContainer = GeistDarkGray1,
    surfaceVariant = GeistDarkGray2,
    surfaceContainerHighest = GeistDarkGray3,

    primary = GeistDarkGray10,
    onPrimary = Color.Black
)

@Composable
fun CruxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    isDynamicColorEnabled: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDynamicColorEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}