package com.example.scanner.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = LogoGreen,
    secondary = LinkBlue,
    tertiary = LogoGreen, // optional accent
    background = LightBackground,
    surface = LightCardBackground,
    error = ErrorRed,

    onPrimary = AppBlack,
    onSecondary = AppBlack,
    onTertiary = AppBlack,
    onBackground = LightText,
    onSurface = LightText,
    onError = AppBlack
)

private val DarkColorScheme = darkColorScheme(
    primary = LogoGreen,
    secondary = LinkBlue,
    tertiary = LogoGreen,
    background = DarkBackground,
    surface = DarkCardBackground,
    error = ErrorRed,

    onPrimary = DarkText,
    onSecondary = DarkText,
    onTertiary = DarkText,
    onBackground = DarkText,
    onSurface = DarkText,
    onError = DarkText
)

@Composable
fun ScannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
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