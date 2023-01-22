package com.example.multi_table.domain.useCases

import com.example.multi_table.domain.common.MultiplicationExpressionManager
import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.domain.entities.RepetitionState

class GetMultiplicationExpressionWithoutResultUseCase(
    private val expressionManager: MultiplicationExpressionManager,
) {

    operator fun invoke(answerTime: Long, state: RepetitionState): MultiplicationExpression {
        return expressionManager.nextExpression(answerTime = answerTime, repetitionState = state)
    }
}