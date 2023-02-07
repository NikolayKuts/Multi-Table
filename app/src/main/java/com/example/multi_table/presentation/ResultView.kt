package com.example.multi_table.presentation

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.multi_table.R
import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.presentation.theme.MainTheme
import com.example.multi_table.presentation.theme.onAnimationFinished

@Composable
fun ResultView(
    state: MainState.ResultState,
    onWrongButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    val timeState = state.time.collectAsState()

    state.expression?.let { expression ->
        Box(modifier = Modifier.fillMaxSize()) {
            val visibilityState = remember { MutableTransitionState(false) }
            val timerVisibilityState = remember { MutableTransitionState(true) }
            var wrongButtonPressedState by remember { mutableStateOf(false) }
            var nextButtonPressedState by remember { mutableStateOf(false) }
            val changeVisibilityStates: () -> Unit = {
                visibilityState.targetState = false
                timerVisibilityState.targetState = false
            }

            LaunchedEffect(key1 = null) { visibilityState.targetState = true }

            onAnimationFinished(
                visibilityState = visibilityState,
                buttonPressedState = wrongButtonPressedState || nextButtonPressedState
            ) {
                when {
                    wrongButtonPressedState -> onWrongButtonClick()
                    nextButtonPressedState -> onNextButtonClick()
                }
            }

            AnimatableTimer(
                timerVisibilityState = timerVisibilityState,
                timeState = timeState,
                dotsAnimationEnabled = false
            )

            AnimatableExpression(visibilityState = visibilityState) {
                ExpressionResultElements(expression = expression)
            }

            AnimatableButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .buttonPadding(),
                visibilityState = visibilityState,
                onWrongButtonClick = {
                    wrongButtonPressedState = true
                    changeVisibilityStates()
                },
                onNextButtonClick = {
                    nextButtonPressedState = true
                    changeVisibilityStates()
                },
            )
        }
    }
}

@Composable
private fun ExpressionResultElements(expression: MultiplicationExpression) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ExpressionElements(expression = expression)

        Text(
            text = stringResource(R.string.text_equal_sign),
            modifier = Modifier.padding(start = 8.dp),
            style = MainTheme.typographies.expressionResultTextStyle
        )

        Text(
            text = expression.product.toString(),
            modifier = Modifier.padding(start = 8.dp),
            style = MainTheme.typographies.expressionResultTextStyle
        )
    }
}

@Composable
private fun AnimatableButtons(
    visibilityState: MutableTransitionState<Boolean>,
    modifier: Modifier,
    onWrongButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    Column(modifier = modifier) {
        AnimatableButton(
            textId = R.string.button_text_wrong,
            visibleState = visibilityState,
            background = MainTheme.colors.wrongButton,
            onClick = onWrongButtonClick
        )

        Spacer(modifier = Modifier.size(size = 16.dp))

        AnimatableButton(
            textId = R.string.button_text_next,
            visibleState = visibilityState,
            background = MainTheme.colors.nextButton,
            reversed = true,
            onClick = onNextButtonClick
        )
    }
}

@Composable
private fun AnimatableButton(
    @StringRes textId: Int,
    visibleState: MutableTransitionState<Boolean>,
    background: Color,
    enterDuration: Int = COMMON_ANIMATION_DURATION,
    exitDuration: Int = COMMON_ANIMATION_DURATION,
    reversed: Boolean = false,
    onClick: () -> Unit,
) {
    val sign = if (reversed) -1 else 1

    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = enterDuration),
            initialOffsetX = { fullWidth -> sign * fullWidth },
        ) + fadeIn(),
        exit = slideOutHorizontally(
            animationSpec = tween(durationMillis = exitDuration),
            targetOffsetX = { fullWidth -> sign * fullWidth },
        ) + fadeOut(),
    ) {
        AppButton(
            textId = textId,
            background = background,
            onClick = onClick
        )
    }
}
