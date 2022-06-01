package com.example.multi_table.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multi_table.domain.entities.Timer
import com.example.multi_table.domain.entities.useCases.GetMultiplicationExpressionAfterWrongOneUseCase
import com.example.multi_table.domain.entities.useCases.GetMultiplicationExpressionWithResultUseCase
import com.example.multi_table.domain.entities.useCases.GetMultiplicationExpressionWithoutResultUseCase
import com.example.multi_table.presentation.MainState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val timer: Timer,
    private val getMultiplicationExpressionWithoutResult
    : GetMultiplicationExpressionWithoutResultUseCase,
    private val getMultiplicationExpressionWithResult: GetMultiplicationExpressionWithResultUseCase,
    private val getMultiplicationExpressionAfterWrongOne
    : GetMultiplicationExpressionAfterWrongOneUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _state = MutableStateFlow<MainState>(StartState)
    val state = _state.asStateFlow()

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        timer.pause()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        timer.resume(scope = viewModelScope)
    }

    fun sendEvent(event: MainEvent) {
        when (event) {
            is MainEvent.NextExpression -> {
                _state.value = QuestionedState(
                    expression = getMultiplicationExpressionWithoutResult(
                        answerTime = timer.time.value
                    ),
                    time = timer.getParsedTimeAsStateFlow(scope = viewModelScope)
                )

                timer.run(scope = viewModelScope)
            }

            is MainEvent.ExpressionResult -> {
                _state.value = ResultState(
                    expression = getMultiplicationExpressionWithResult(),
                    time = timer.getParsedTimeAsStateFlow(scope = viewModelScope)
                )

                timer.stop()
            }

            is MainEvent.WrongAnswer -> {
                _state.value = QuestionedState(
                    expression = getMultiplicationExpressionAfterWrongOne(),
                    time = timer.getParsedTimeAsStateFlow(scope = viewModelScope)
                )

                timer.run(scope = viewModelScope)
            }
        }
    }
}