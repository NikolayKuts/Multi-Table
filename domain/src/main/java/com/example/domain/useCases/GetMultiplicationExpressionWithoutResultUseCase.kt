package com.example.domain.useCases

import com.example.domain.common.MultiplicationExpressionManager
import com.example.domain.entities.MultiplicationExpression
import com.example.domain.entities.RepetitionState

class GetMultiplicationExpressionWithoutResultUseCase(
    private val expressionManager: MultiplicationExpressionManager,
) {

    operator fun invoke(answerTime: Long, state: RepetitionState): MultiplicationExpression {
        return expressionManager.nextExpression(answerTime = answerTime, repetitionState = state)
    }
}