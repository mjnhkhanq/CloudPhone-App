package com.minhkhang.cloudphone.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val DarkColors = darkColorScheme(primary = Color(0xFF3B82F6), onPrimary = Color.White, background = Color(0xFF0F1115), surface = Color(0xFF1A1D24), onBackground = Color(0xFFE8EAED), onSurface = Color(0xFFE8EAED))
@Composable fun CloudPhoneTheme(content: @Composable () -> Unit) { MaterialTheme(colorScheme = DarkColors, typography = Typography(), content = content) }
