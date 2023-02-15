package com.example.presentation

import com.example.domain.entities.MultiplicationExpression
import kotlinx.coroutines.flow.StateFlow

sealed class MainState {

    object StartState : MainState()

    class QuestionedState(
        val expression: MultiplicationExpression,
        val time: StateFlow<com.example.domain.common.Timer.Time>
    ) : MainState()

    class ResultState(
        val expression: MultiplicationExpression?,
        val time: StateFlow<com.example.domain.common.Timer.Time>
    ) : MainState()
}