package com.example.presentation

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import com.example.multi_table.presentation.theme.onAnimationFinished

@Composable
fun BoxScope.BeginningView(onStartButtonClick: () -> Unit) {
        val visibilityState = remember { MutableTransitionState(false) }
        var startButtonPressedState by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = null) { visibilityState.targetState = true }

        onAnimationFinished(
            visibilityState = visibilityState,
            buttonPressedState = startButtonPressedState,
        ) {
            onStartButtonClick()
        }

        AnimatableBottomButton(
            visible = visibilityState,
            textId = R.string.button_text_start,
            onClick = {
                visibilityState.targetState = false
                startButtonPressedState = true
            },
        )
}