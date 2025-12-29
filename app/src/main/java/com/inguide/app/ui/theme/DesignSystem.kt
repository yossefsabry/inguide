package com.inguide.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Centralized Design System for the Application.
 * Edit this file to change colors, typography, shapes, and dimensions globally.
 */
object DesignSystem {

    object Colors {
        // Primary Brand Colors
        val Primary = Color(0xFF007AFF)
        val PrimaryDark = Color(0xFF0051D5)
        
        // Secondary Brand Colors
        val Secondary = Color(0xFFFF9500)
        val SecondaryDark = Color(0xFFFF6B00)

        // Status Colors
        val Success = Color(0xFF34C759)
        val SuccessDark = Color(0xFF248A3D)
        val Error = Color(0xFFFF3B30)
        val Warning = Color(0xFFFFD60A)

        // Gradients
        val GradientStart = Color(0xFF007AFF)
        val GradientEnd = Color(0xFF5856D6)
        
        // Shimmer
        val ShimmerLight = Color(0xFFF0F0F0)
        val ShimmerDark = Color(0xFF2C2C2E)

        // Feature Colors
        val FeatureGreen = Color(0xFF34C759)
        val FeaturePurple = Color(0xFFAF52DE)
        val FeatureOrange = Color(0xFFFF9500)
        val FeatureBlue = Color(0xFF007AFF)

        // Dashboard Specific Colors (Dark aesthetics)
        val DashboardBackground = Color(0xFF0F111A)
        val DashboardCardBackground = Color(0xFF1E2230)
        val DashboardWarningRed = Color(0xFFFF3B30) // Same as Error but explicit for dashboard
        val DashboardCautionYellow = Color(0xFFFFCC00)

        // Light Theme Specifics
        val Light = ThemeColors(
            primary = Primary,
            secondary = Secondary, 
            tertiary = Success,
            background = Color(0xFFF2F2F7),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFFFFFFF), // Card Background
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.Black,
            onSurface = Color.Black,
            outline = Color(0xFFE0E0E0),
            error = Error
        )

        // Dark Theme Specifics
        val Dark = ThemeColors(
            primary = Primary,
            secondary = Secondary,
            tertiary = Success,
            background = Color(0xFF121212),
            surface = Color(0xFF1C1C1E),
            surfaceVariant = Color(0xFF2C2C2E), // Card Background
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White,
            outline = Color(0xFF38383A),
            error = Error
        )
    }

    object Typography {
        val Default = Typography(
            displayLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                lineHeight = 40.sp
            ),
            displayMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 34.sp
            ),
            headlineMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 26.sp
            ),
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp
            ),
            labelMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        )
    }

    object Shapes {
        // Change these values to affect global corner roundness
        val ButtonCornerRadius = 12.dp
        val CardCornerRadius = 16.dp
        val InputCornerRadius = 12.dp
        val BottomSheetCornerRadius = 24.dp

        val ButtonShape = RoundedCornerShape(ButtonCornerRadius)
        val CardShape = RoundedCornerShape(CardCornerRadius)
        val InputShape = RoundedCornerShape(InputCornerRadius)
        val BottomSheetShape = RoundedCornerShape(topStart = BottomSheetCornerRadius, topEnd = BottomSheetCornerRadius)
        
        // Special Shapes
        val LargeIconShape = RoundedCornerShape(40.dp)
        val MessageBubbleUser = RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp)
        val MessageBubbleBot = RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp)
    }

    object Dimensions {
        val PaddingSmall = 8.dp
        val PaddingMedium = 16.dp
        val PaddingLarge = 24.dp
        val IconSizeSmall = 16.dp
        val IconSizeMedium = 24.dp
    }
}

data class ThemeColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val outline: Color,
    val error: Color
)
