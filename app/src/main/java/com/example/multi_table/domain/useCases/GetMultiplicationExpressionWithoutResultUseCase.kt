package com.example.multi_table.domain.useCases

import com.example.multi_table.domain.common.MultiplicationExpressionManager
import com.example.multi_table.domain.entities.MultiplicationExpression

class GetMultiplicationExpressionWithoutResultUseCase(
    private val expressionManager: MultiplicationExpressionManager,
) {

    operator fun invoke(answerTime: Long): MultiplicationExpression {
        return expressionManager.nextExpression(answerTime = answerTime)
    }
}