package com.example.multi_table.presentation

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.multi_table.R
import com.example.multi_table.domain.common.Timer
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
fun BoxScope.AnimatableExpression(
    visibilityState: MutableTransitionState<Boolean>,
    expressionContent: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier.align(Alignment.Center),
        visibleState = visibilityState,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = COMMON_ANIMATION_DURATION),
            initialOffsetX = { fullWidth -> -fullWidth * 2 },
        )
                + fadeIn(),
        exit = slideOutHorizontally(
            animationSpec = tween(durationMillis = COMMON_ANIMATION_DURATION),
            targetOffsetX = { fullWidth -> fullWidth * 2 },
        )
                + fadeOut(),
        content = expressionContent
    )
}

@Composable
fun BoxScope.AnimatableBottomButton(
    visible: MutableTransitionState<Boolean>,
    @StringRes textId: Int,
    onClick: () -> Unit,
    enterDuration: Int = 1000,
    exitDuration: Int = 200,
) {
    AnimatedVisibility(
        modifier = Modifier
            .align(alignment = Alignment.BottomCenter)
            .buttonPadding(),
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
            onClick = onClick
        )
    }
}

@Composable
fun BoxScope.AnimatableTimer(
    timerVisibilityState: MutableTransitionState<Boolean>,
    timeState: State<Timer.Time>,
    dotsAnimationEnabled: Boolean = true
) {
    AnimatedVisibility(
        modifier = Modifier
            .align(alignment = Alignment.TopCenter)
            .padding(top = (LocalConfiguration.current.screenHeightDp / 16).dp),
        visibleState = timerVisibilityState,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = COMMON_ANIMATION_DURATION),
            initialOffsetY = { fullWidth -> -fullWidth },
        ) + fadeIn(),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = COMMON_ANIMATION_DURATION),
            targetOffsetY = { fullWidth -> -fullWidth },
        ) + fadeOut(),
    ) {
        TimeView(
            seconds = timeState.value.seconds,
            millis = timeState.value.millis,
            dotsAnimationEnabled = dotsAnimationEnabled
        )
    }
}