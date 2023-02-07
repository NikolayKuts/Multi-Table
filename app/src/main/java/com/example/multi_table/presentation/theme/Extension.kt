package com.example.multi_table.presentation.theme

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.MutableState

fun onAnimationFinished(
    visibilityState: MutableTransitionState<Boolean>,
    buttonPressedState: Boolean,
    onFinish: () -> Unit,
) {
    if (
        !visibilityState.targetState &&
        !visibilityState.currentState &&
        buttonPressedState
    ) {
        onFinish()
    }
}