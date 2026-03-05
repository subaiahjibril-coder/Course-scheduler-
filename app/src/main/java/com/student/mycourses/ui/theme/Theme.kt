package com.student.mycourses.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = MintGreenDark,
    onPrimary = White,
    primaryContainer = MintGreenLight,
    onPrimaryContainer = DarkText,
    secondary = LightYellowAccent,
    onSecondary = DarkText,
    secondaryContainer = LightYellow,
    onSecondaryContainer = DarkText,
    tertiary = SoftPinkAccent,
    onTertiary = White,
    tertiaryContainer = SoftPink,
    onTertiaryContainer = DarkText,
    background = OffWhite,
    onBackground = DarkText,
    surface = White,
    onSurface = DarkText,
    surfaceVariant = LightGray,
    onSurfaceVariant = SubtitleText,
    outline = MediumGray
)

@Composable
fun MyCoursesTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
