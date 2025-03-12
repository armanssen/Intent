package com.crux.ui.theme

import android.os.Build
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.crux.data.datastore.PreferenceKeys
import com.crux.data.datastore.appPreferences
import com.crux.domain.model.AppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

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
    appTheme: AppTheme,
    isDynamicColorEnabled: Boolean,  // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {

    val isDarkThemeEnabled = when (appTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme()
    }

    val colorScheme = when {
        isDynamicColorEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkThemeEnabled) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        isDarkThemeEnabled -> {
            DarkColorScheme
        }
        else -> {
            LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}