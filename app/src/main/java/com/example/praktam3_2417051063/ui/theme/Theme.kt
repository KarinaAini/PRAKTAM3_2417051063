package com.example.praktam3_2417051063.ui.theme

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

private val AppcolorScheme = lightColorScheme(
    primary = NavyGreenPrimary,
    secondary = OliveSecondary,
    background = CreamBackground,
    surface = CardSurface,
    onPrimary = OnPrimaryText
)

@Composable
fun PRAKTAM3_2417051063Theme(content: @Composable () -> Unit){
    MaterialTheme(
        colorScheme = AppcolorScheme,
        typography = Typography,
        content = content
    )
}