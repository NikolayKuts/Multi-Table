package com.example.multi_table.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class MainTopographies(
    val materialTypographies: Typography,
    val expressionTextStyle: TextStyle,
    val timerTextStyle: TextStyle,
    val multiplicationSignTextStyle: TextStyle,
    val expressionResultTextStyle: TextStyle,
)

private val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    button = TextStyle(
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Serif,
    ),
)

private val LightExpressionTextStyle = TextStyle(
    color = LightMainPalettes.expressionElement,
    fontSize = 40.sp,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.Bold
)

private val LightTimerTextStyle = TextStyle(
    color = LightMainPalettes.timer,
    fontStyle = FontStyle.Italic,
    fontSize = 28.sp,
)

private val LightMultiplicationSighTextStyle = TextStyle(
    color = LightMainPalettes.multiplicationSing,
    fontSize = 28.sp,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.Bold
)

private val LightExpressionResultTextStyle = TextStyle(
    color = LightMainPalettes.expressionResultElement,
    fontSize = 40.sp,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.Bold
)

private val DarkTimerTextStyle = TextStyle(
    color = DarkMainPalettes.timer,
    fontStyle = FontStyle.Italic,
    fontSize = 28.sp,
)

private val DarkExpressionTextStyle = TextStyle(
    color = DarkMainPalettes.expressionElement,
    fontSize = 40.sp,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.Bold
)

private val DarkMultiplicationSighTextStyle = TextStyle(
    color = DarkMainPalettes.multiplicationSing,
    fontSize = 28.sp,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.Bold
)

private val DarkExpressionResultTextStyle = TextStyle(
    color = DarkMainPalettes.expressionResultElement,
    fontSize = 40.sp,
    fontStyle = FontStyle.Italic,
    fontWeight = FontWeight.Bold
)

val LightMainTypographies = MainTopographies(
    materialTypographies = Typography,
    expressionTextStyle = LightExpressionTextStyle,
    timerTextStyle = LightTimerTextStyle,
    multiplicationSignTextStyle = LightMultiplicationSighTextStyle,
    expressionResultTextStyle = LightExpressionResultTextStyle
)

val DarkMainTypographies = MainTopographies(
    materialTypographies = Typography,
    expressionTextStyle = DarkExpressionTextStyle,
    timerTextStyle = DarkTimerTextStyle,
    multiplicationSignTextStyle = DarkMultiplicationSighTextStyle,
    expressionResultTextStyle = DarkExpressionResultTextStyle
)
