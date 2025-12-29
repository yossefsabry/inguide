package com.inguide.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = DesignSystem.Colors.Light.primary,
    secondary = DesignSystem.Colors.Light.secondary,
    tertiary = DesignSystem.Colors.Light.tertiary,
    background = DesignSystem.Colors.Light.background,
    surface = DesignSystem.Colors.Light.surface,
    surfaceVariant = DesignSystem.Colors.Light.surfaceVariant,
    onPrimary = DesignSystem.Colors.Light.onPrimary,
    onSecondary = DesignSystem.Colors.Light.onSecondary,
    onBackground = DesignSystem.Colors.Light.onBackground,
    onSurface = DesignSystem.Colors.Light.onSurface,
    outline = DesignSystem.Colors.Light.outline, // Custom mapping if needed, though Maps to outline variant often
    error = DesignSystem.Colors.Light.error
)

private val DarkColorScheme = darkColorScheme(
    primary = DesignSystem.Colors.Dark.primary,
    secondary = DesignSystem.Colors.Dark.secondary,
    tertiary = DesignSystem.Colors.Dark.tertiary,
    background = DesignSystem.Colors.Dark.background,
    surface = DesignSystem.Colors.Dark.surface,
    surfaceVariant = DesignSystem.Colors.Dark.surfaceVariant,
    onPrimary = DesignSystem.Colors.Dark.onPrimary,
    onSecondary = DesignSystem.Colors.Dark.onSecondary,
    onBackground = DesignSystem.Colors.Dark.onBackground,
    onSurface = DesignSystem.Colors.Dark.onSurface,
    outline = DesignSystem.Colors.Dark.outline,
    error = DesignSystem.Colors.Dark.error
)

@Composable
fun InGuideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DesignSystem.Typography.Default,
        // We can also apply shapes here if we define them in MaterialTheme
        // shapes = MaterialTheme.shapes.copy(...) 
        // But for now we just use DesignSystem.Shapes directly in components or map here if desired.
        // Let's map small/medium/large to our shapes for better integration.
        shapes = MaterialTheme.shapes.copy(
            small = DesignSystem.Shapes.InputShape,
            medium = DesignSystem.Shapes.CardShape,
            large = DesignSystem.Shapes.BottomSheetShape
        ),
        content = content
    )
}
