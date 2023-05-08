package com.kuts.domain.useCases

import com.kuts.domain.common.MultiplicationExpressionManager
import com.kuts.domain.entities.MultiplicationExpression
import com.kuts.domain.entities.RepetitionState

class GetMultiplicationExpressionWithoutResultUseCase(
    private val expressionManager: MultiplicationExpressionManager,
) {

    operator fun invoke(answerTime: Long, state: RepetitionState): MultiplicationExpression {
        return expressionManager.nextExpression(answerTime = answerTime, repetitionState = state)
    }
}