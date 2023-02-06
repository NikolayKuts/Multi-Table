package com.example.multi_table.presentation

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.multi_table.R
import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.presentation.theme.MainTheme

@Composable
fun AppButton(
    @StringRes textId: Int,
    background: Color = MainTheme.colors.materialColors.primary,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = background),
        onClick = onClick
    ) {
        Text(text = stringResource(textId))
    }
}

fun Modifier.buttonPadding(): Modifier = this.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)

@Composable
fun TimeView(
    modifier: Modifier = Modifier,
    seconds: Int,
    millis: Int,
    dotsAnimationEnabled: Boolean = false
) {
    Row(modifier = modifier) {
        Text(
            text = seconds.toString(),
            style = MainTheme.typographies.timerTextStyle
        )

        val animatedAlpha = if (dotsAnimationEnabled) {
            animateFloatAsState(targetValue = if (millis % 9 != 0) 1f else 0F).value
        } else {
            1F
        }

        Text(
            text = stringResource(R.string.text_colon),
            modifier = Modifier
                .alpha(animatedAlpha)
                .padding(start = 4.dp, end = 4.dp)
                .offset(y = (-2.5).dp),
            style = MainTheme.typographies.timerTextStyle
        )

        Text(
            text = millis.toString(),
            style = MainTheme.typographies.timerTextStyle
        )
    }
}

@Composable
fun ExpressionElements(
    expression: MultiplicationExpression,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExpressionElement(text = expression.multiplicand.toString())

        Text(
            text = stringResource(R.string.text_multiplication_sign),
            modifier = Modifier.padding(8.dp),
            style = MainTheme.typographies.multiplicationSignTextStyle
        )

        ExpressionElement(text = expression.multiplier.toString())
    }
}

@Composable
private fun ExpressionElement(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        style = MainTheme.typographies.expressionTextStyle
    )
}

@Composable
fun AnimatableBottomButton(
    @StringRes textId: Int,
    enterDuration: Int = 1000,
    exitDuration: Int = 200,
    onClick: () -> Unit,
) {
    val visible = remember { MutableTransitionState(false) }
    val nextButtonPressState = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = null) { visible.targetState = true }

    if (
        !visible.targetState &&
        !visible.currentState &&
        nextButtonPressState.value
    ) {
        onClick()
    }

    AnimatedVisibility(
        visibleState = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = enterDuration),
            initialOffsetY = { fullWidth -> fullWidth },
        ) + fadeIn(),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = exitDuration),
            targetOffsetY = { fullWidth -> fullWidth },
        ) + fadeOut(),
    ) {
        AppButton(
            textId = textId,
            onClick = {
                visible.targetState = false
                nextButtonPressState.value = true
            }
        )
    }
}