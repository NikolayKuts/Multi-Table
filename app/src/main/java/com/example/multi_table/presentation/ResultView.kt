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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.multi_table.R
import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.presentation.theme.MainTheme

@Composable
fun ResultView(
    state: MainState.ResultState,
    onWrongButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    val timeState = state.time.collectAsState()
    state.expression?.let { expression ->
        Box(modifier = Modifier.fillMaxSize()) {
            val visible = remember { MutableTransitionState(true) }

            AnimatedVisibility(
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .padding(top = (LocalConfiguration.current.screenHeightDp / 16).dp),
                visibleState = visible,
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = 100),
                    targetOffsetY = { fullWidth -> -fullWidth},
                ) + fadeOut(),
            ) {
                TimeView(
                    seconds = timeState.value.seconds,
                    millis = timeState.value.millis,
                )
            }

            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExpressionResultElements(expression = expression)
            }

            AnimatableButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .buttonPadding(),
                onWrongButtonClick = onWrongButtonClick,
                onNextButtonClick = onNextButtonClick,
                onStartAnimation = { visible.targetState = false }
            )
        }
    }
}

@Composable
private fun ExpressionResultElements(expression: MultiplicationExpression) {
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

@Composable
private fun AnimatableButtons(
    modifier: Modifier,
    onWrongButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onStartAnimation: () -> Unit = {},
) {
    val visible = remember { MutableTransitionState(false) }
    val wrongButtonPressState = remember { mutableStateOf(false) }
    val nextButtonPressState = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = null) { visible.targetState = true }

    if (
        !visible.targetState &&
        !visible.currentState &&
        (wrongButtonPressState.value || nextButtonPressState.value)
    ) {
        when {
            wrongButtonPressState.value -> onWrongButtonClick()
            nextButtonPressState.value -> onNextButtonClick()
        }
    }

    Column(modifier = modifier) {
        AnimatableButton(
            textId = R.string.button_text_wrong,
            visibleState = visible,
            buttonPressState = wrongButtonPressState,
            background = MainTheme.colors.wrongButton,
            onStartAnimation = onStartAnimation,
        )

        Spacer(modifier = Modifier.size(size = 16.dp))

        AnimatableButton(
            textId = R.string.button_text_next,
            visibleState = visible,
            buttonPressState = nextButtonPressState,
            background = MainTheme.colors.nextButton,
            reversed = true,
            onStartAnimation = onStartAnimation,
        )
    }
}

@Composable
private fun AnimatableButton(
    @StringRes textId: Int,
    visibleState: MutableTransitionState<Boolean>,
    buttonPressState: MutableState<Boolean>,
    background: Color,
    enterDuration: Int = 100,
    exitDuration: Int = 100,
    reversed: Boolean = false,
    onStartAnimation: () -> Unit = {}
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
            onClick = {
                onStartAnimation()
                visibleState.targetState = false
                buttonPressState.value = true
            }
        )
    }
}
