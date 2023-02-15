package com.example.multi_table.domain.common

import com.example.domain.common.entities.MultiplicationExpression
import com.example.domain.common.entities.MultiplicationExpressionHolder
import com.example.domain.common.entities.RepetitionState.SUCCESS
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class MultiplicationExpressionManagerTest {

    @Test
    fun `noneQueue is initialized`() {
        val expressionManager = com.example.domain.common.MultiplicationExpressionManager()

        expressionManager.nextExpression(answerTime = 0, repetitionState = SUCCESS)
    }

    @Test
    fun `current expression is not empty after first call of nextExpression`() {
        val expressionManager = com.example.domain.common.MultiplicationExpressionManager()

        expressionManager.nextExpression(answerTime = 0, repetitionState = SUCCESS)
        assertNotNull(expressionManager.currentExpression)
    }

    @Test
    fun `immediatelyQueue is not empty when answer time is over immediately limit`() {
        val immediatelyQueue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isImmediatelyQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = immediatelyQueue,
            answerTime = com.example.domain.common.MemorizationLevel.IMMEDIATELY.timeout
        )

        assertEquals(true, isQueueNotEmpty)
    }

    @Test
    fun `immediatelyQueue is empty when answer time is not over immediately limit`() {
        val immediatelyQueue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isImmediatelyQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = immediatelyQueue,
            answerTime = com.example.domain.common.MemorizationLevel.HURRY.timeout
        )

        assertEquals(false, isQueueNotEmpty)
    }

    @Test
    fun `hurryQueue is not empty when answer time is over immediately limit`() {
        val hurryQueue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isHurryQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = hurryQueue,
            answerTime = com.example.domain.common.MemorizationLevel.HURRY.timeout
        )

        assertEquals(true, isQueueNotEmpty)
    }

    @Test
    fun `hurryQueue is empty when answer time is not over immediately limit`() {
        val hurryQueue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isHurryQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = hurryQueue,
            answerTime = com.example.domain.common.MemorizationLevel.SOON.timeout
        )

        assertEquals(false, isQueueNotEmpty)
    }

    @Test
    fun `soonQueue is not empty when answer time is over limit`() {
        val soonQueue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isSoonQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = soonQueue,
            answerTime = com.example.domain.common.MemorizationLevel.SOON.timeout
        )

        assertEquals(true, isQueueNotEmpty)
    }

    @Test
    fun `soonQueue is empty when answer time is not over limit`() {
        val soonQueue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isSoonQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = soonQueue,
            answerTime = com.example.domain.common.MemorizationLevel.NONE.timeout
        )

        assertEquals(false, isQueueNotEmpty)
    }


    private fun isImmediatelyQueueNotEmptyWhenAnswerTimeIsOverLimit(
        queue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder>,
        answerTime: Long
    ): Boolean {
        val currentExpression = com.example.domain.common.entities.MultiplicationExpression(
            multiplicand = 2,
            multiplier = 3
        )
        val expressionManager = com.example.domain.common.MultiplicationExpressionManager(
            immediatelyQueue = queue,
            currentExpression = currentExpression,
        )

        expressionManager.nextExpression(
            answerTime = answerTime,
            repetitionState = SUCCESS
        )

        return queue.isNotEmpty()
    }

    private fun isHurryQueueNotEmptyWhenAnswerTimeIsOverLimit(
        queue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder>,
        answerTime: Long
    ): Boolean {
        val currentExpression = com.example.domain.common.entities.MultiplicationExpression(
            multiplicand = 2,
            multiplier = 3
        )
        val expressionManager = com.example.domain.common.MultiplicationExpressionManager(
            hurryQueue = queue,
            currentExpression = currentExpression,
        )

        expressionManager.nextExpression(
            answerTime = answerTime,
            repetitionState = SUCCESS
        )

        return queue.isNotEmpty()
    }

    private fun isSoonQueueNotEmptyWhenAnswerTimeIsOverLimit(
        queue: Queue<com.example.domain.common.entities.MultiplicationExpressionHolder>,
        answerTime: Long
    ): Boolean {
        val currentExpression = com.example.domain.common.entities.MultiplicationExpression(
            multiplicand = 2,
            multiplier = 3
        )
        val expressionManager = com.example.domain.common.MultiplicationExpressionManager(
            soonQueue = queue,
            currentExpression = currentExpression,
        )

        expressionManager.nextExpression(
            answerTime = answerTime,
            repetitionState = SUCCESS
        )

        return queue.isNotEmpty()
    }
}