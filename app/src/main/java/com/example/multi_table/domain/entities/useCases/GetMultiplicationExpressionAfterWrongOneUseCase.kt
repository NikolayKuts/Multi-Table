package com.example.multi_table.domain.entities.useCases

import com.example.multi_table.domain.MultiplicationExpressionManager
import com.example.multi_table.domain.entities.MultiplicationExpression

class GetMultiplicationExpressionAfterWrongOneUseCase(
    private val expressionManager: MultiplicationExpressionManager,
) {

    operator fun invoke(): MultiplicationExpression {
        return expressionManager.nextExpressionAfterWrongOne()
    }
}