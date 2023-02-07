package com.example.multi_table.presentation

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.multi_table.R
import com.example.multi_table.presentation.theme.onAnimationFinished

@Composable
fun BeginningView(onStartButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
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
}