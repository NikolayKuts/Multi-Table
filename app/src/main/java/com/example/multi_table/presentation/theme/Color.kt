package com.example.multi_table.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

data class MainColors(
    val materialColors: Colors,
    val expressionElement: Color,
    val multiplicationSing: Color,
    val expressionResultElement: Color,
    val timer: Color,
)

val LightMainPalettes = MainColors(
    materialColors = lightColors(
        primary = Color(0xFF8BC34A)
    ),
    expressionElement = Color(0xFF65A8C7),
    multiplicationSing = Color(0xFFD3C446),
    expressionResultElement = Color(0xD7DB7B75),
    timer = Color(0xFF7A7A7A)
)

val DarkMainPalettes = MainColors(
    materialColors = darkColors(
        primary = Color(0xFFC2665E),
        onPrimary = Color(0xFFFFFFFF),
        primaryVariant = Color(0xFF89F53C),
        secondary = Color(0xFF4CAF50),
        onSecondary = Color(0xFFEFF53C)
    ),
    expressionElement = Color(0xFF65A8C7),
    multiplicationSing = Color(0xFFC7B94F),
    expressionResultElement = Color(0xBF8BC34A),
    timer = Color(0xFFB3B3B3)
)