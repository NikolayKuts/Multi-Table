package com.example.multi_table.domain.common

import org.junit.Test
import kotlin.test.assertNotNull

internal class MultiplicationExpressionManagerTest {

    @Test()
    fun `none@ueue is initialization`() {
        val expressionManager = MultiplicationExpressionManager()

        expressionManager.nextExpression(answerTime = 0)
    }

    @Test
    fun `current expression is not empty after first call of nextExpression`() {
        val expressionManager = MultiplicationExpressionManager()

        expressionManager.nextExpression(answerTime = 0)
        assertNotNull(expressionManager.currentExpression)
    }
}