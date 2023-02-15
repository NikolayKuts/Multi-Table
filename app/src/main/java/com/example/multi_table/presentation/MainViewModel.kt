package com.example.multi_table.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.RepetitionState
import com.example.multi_table.presentation.MainState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val timer: com.example.domain.common.Timer,
    private val getMultiplicationExpressionWithoutResult
    : com.example.domain.useCases.GetMultiplicationExpressionWithoutResultUseCase,
    private val getMultiplicationExpressionWithResult: com.example.domain.useCases.GetMultiplicationExpressionWithResultUseCase,
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
                        answerTime = timer.time.value,
                        state = RepetitionState.SUCCESS
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
                    expression = getMultiplicationExpressionWithoutResult(
                        answerTime = timer.time.value,
                        state = RepetitionState.FAILURE
                    ),
                    time = timer.getParsedTimeAsStateFlow(scope = viewModelScope)
                )

                timer.run(scope = viewModelScope)
            }
        }
    }
}