package com.example.multi_table.presentation

import com.example.multi_table.domain.MultiplicationExpression
import com.example.multi_table.domain.Timer
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