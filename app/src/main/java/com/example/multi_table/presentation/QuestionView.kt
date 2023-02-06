package com.example.multi_table.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.multi_table.R

private const val ANIMATION_DURATION = 100

@Composable
fun QuestionView(
    state: MainState.QuestionedState,
    onResultButtonClick: () -> Unit,
) {
    val timeState = state.time.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        val visible = remember { MutableTransitionState(false) }

        LaunchedEffect(key1 = null) { visible.targetState = true }

        AnimatedVisibility(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .padding(top = (LocalConfiguration.current.screenHeightDp / 16).dp),
            visibleState = visible,
            enter = slideInVertically(
                animationSpec = tween(durationMillis = ANIMATION_DURATION),
                initialOffsetY = { fullWidth -> -fullWidth },
            ) + fadeIn(),
            exit = ExitTransition.None
        ) {
            TimeView(
                seconds = timeState.value.seconds,
                millis = timeState.value.millis,
                dotsAnimationEnabled = true
            )
        }

        ExpressionElements(
            expression = state.expression,
            modifier = Modifier.align(alignment = Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .buttonPadding(),
        ) {
            AnimatableBottomButton(
                textId = R.string.button_text_result,
                enterDuration = ANIMATION_DURATION,
                exitDuration = ANIMATION_DURATION,
                onClick = onResultButtonClick,
            )
        }
    }
}