package com.example.domain.useCases

import com.example.domain.common.MultiplicationExpressionManager
import com.example.domain.entities.MultiplicationExpression

class GetMultiplicationExpressionWithResultUseCase(
    private val expressionManager: MultiplicationExpressionManager,
) {

    operator fun invoke(): MultiplicationExpression? {
        return expressionManager.currentExpression
    }
}