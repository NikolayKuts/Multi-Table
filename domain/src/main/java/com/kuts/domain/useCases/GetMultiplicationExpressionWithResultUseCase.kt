package com.kuts.domain.useCases

import com.kuts.domain.common.MultiplicationExpressionManager
import com.kuts.domain.entities.MultiplicationExpression

class GetMultiplicationExpressionWithResultUseCase(
    private val expressionManager: MultiplicationExpressionManager,
) {

    operator fun invoke(): MultiplicationExpression? {
        return expressionManager.currentExpression
    }
}