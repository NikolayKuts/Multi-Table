package com.example.multi_table.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multi_table.domain.MultiplicationExpression
import com.example.multi_table.domain.MultiplicationExpressionManager
import com.example.multi_table.domain.Timer
import com.example.multi_table.presentation.MainState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val expressionManager = MultiplicationExpressionManager()
    private val timer = Timer(coroutineScope = viewModelScope)

    private val _state = MutableStateFlow<MainState>(StartState)
    val state = _state.asStateFlow()

    fun sendEvent(event: MainEvent) {
        when (event) {
            is MainEvent.NextExpression -> {
                _state.value = QuestionedState(
                    expression = expressionManager.nextExpression(answerTime = timer.time.value),
                    time = timer.parsedTime
                )

                timer.run()
            }

            is MainEvent.ExpressionResult -> {

                _state.value = ResultState(
                    expression = expressionManager.currentExpression,
                    time = timer.parsedTime
                )
                timer.pause()
            }

            is MainEvent.WrongAnswer -> {
                _state.value = QuestionedState(
                    expression = expressionManager.nextExpressionAfterWrongOne(),
                    time = timer.parsedTime
                )

                timer.run()
            }
        }

    }
}