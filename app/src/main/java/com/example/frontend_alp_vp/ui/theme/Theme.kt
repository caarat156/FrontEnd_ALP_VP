package com.example.frontend_alp_vp.ui.theme

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

private val ColorScheme = lightColorScheme(
    primary = primer,
    secondary = sekunder,
    tertiary = tersier,
    background = White,
    surface = White,
    onPrimary = White,      // Text on top of Red
    onSecondary = White,    // Text on top of Brown
    onTertiary = Black,     // Text on top of Beige
    onBackground = Black,   // Text on top of Background
    onSurface = Black       // Text on top of Cards
)

@Composable
fun FrontEnd_ALP_VPTheme(
    content: @Composable () -> Unit
) {
    // We just pass your specific ColorScheme directly.
    // No if/else logic needed!
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}