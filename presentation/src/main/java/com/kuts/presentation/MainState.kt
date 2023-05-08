package com.kuts.presentation

import com.kuts.domain.common.Timer
import com.kuts.domain.entities.MultiplicationExpression
import kotlinx.coroutines.flow.StateFlow

sealed class MainState {

    object StartState : MainState()

    class QuestionedState(
        val expression: MultiplicationExpression,
        val time: StateFlow<Timer.Time>
    ) : MainState()

    class ResultState(
        val expression: MultiplicationExpression?,
        val time: StateFlow<Timer.Time>
    ) : MainState()
}