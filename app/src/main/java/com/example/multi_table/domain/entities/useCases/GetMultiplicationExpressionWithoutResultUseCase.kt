package com.example.multi_table.domain.entities.useCases

import com.example.multi_table.domain.MultiplicationExpressionManager
import com.example.multi_table.domain.entities.MultiplicationExpression

class GetMultiplicationExpressionWithoutResultUseCase(
    private val expressionManager: MultiplicationExpressionManager
) {

    operator fun invoke(answerTime: Long): MultiplicationExpression {
        return expressionManager.nextExpression(answerTime = answerTime)
    }
}