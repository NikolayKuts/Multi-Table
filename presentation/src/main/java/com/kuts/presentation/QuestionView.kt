package com.kuts.presentation

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuts.presentation.theme.onAnimationFinished

@Composable
fun BoxScope.QuestionView(
    screenState: MainState.QuestionedState,
    onResultButtonClick: () -> Unit,
) {
    val timeState = screenState.time.collectAsState()
    val visibilityState = remember { MutableTransitionState(false) }
    val timerVisibilityState = remember { MutableTransitionState(false) }
    var nextButtonPressedState by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = null) {
        timerVisibilityState.targetState = true
        visibilityState.targetState = true
    }

    onAnimationFinished(
        visibilityState = visibilityState,
        buttonPressedState = nextButtonPressedState,
    ) {
        onResultButtonClick()
    }

    AnimatableTimer(timerVisibilityState = timerVisibilityState, timeState = timeState)

    AnimatableExpression(visibilityState = visibilityState, bottomPadding = 40.dp) {
        ExpressionElements(
            expression = screenState.expression,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }

    AnimatableBottomButton(
        visible = visibilityState,
        textId = R.string.button_text_result,
        enterDuration = COMMON_ANIMATION_DURATION,
        exitDuration = COMMON_ANIMATION_DURATION,
        onClick = {
            nextButtonPressedState = true
            visibilityState.targetState = false
        }
    )
}