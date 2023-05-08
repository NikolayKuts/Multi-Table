package com.kuts.presentation.theme

import androidx.compose.animation.core.MutableTransitionState

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