package com.example.multi_table.domain.common

import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.domain.entities.MultiplicationExpressionHolder
import com.example.multi_table.domain.entities.RepetitionState.SUCCESS
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class MultiplicationExpressionManagerTest {

    @Test
    fun `noneQueue is initialized`() {
        val expressionManager = MultiplicationExpressionManager()

        expressionManager.nextExpression(answerTime = 0, repetitionState = SUCCESS)
    }

    @Test
    fun `current expression is not empty after first call of nextExpression`() {
        val expressionManager = MultiplicationExpressionManager()

        expressionManager.nextExpression(answerTime = 0, repetitionState = SUCCESS)
        assertNotNull(expressionManager.currentExpression)
    }

    @Test
    fun `immediatelyQueue is not empty when answer time is over immediately limit`() {
        val immediatelyQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isImmediatelyQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = immediatelyQueue,
            answerTime = MemorizationLevel.IMMEDIATELY.timeout
        )

        assertEquals(true, isQueueNotEmpty)
    }

    @Test
    fun `immediatelyQueue is empty when answer time is not over immediately limit`() {
        val immediatelyQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isImmediatelyQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = immediatelyQueue,
            answerTime = MemorizationLevel.HURRY.timeout
        )

        assertEquals(false, isQueueNotEmpty)
    }

    @Test
    fun `hurryQueue is not empty when answer time is over immediately limit`() {
        val hurryQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isHurryQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = hurryQueue,
            answerTime = MemorizationLevel.HURRY.timeout
        )

        assertEquals(true, isQueueNotEmpty)
    }

    @Test
    fun `hurryQueue is empty when answer time is not over immediately limit`() {
        val hurryQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isHurryQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = hurryQueue,
            answerTime = MemorizationLevel.SOON.timeout
        )

        assertEquals(false, isQueueNotEmpty)
    }

    @Test
    fun `soonQueue is not empty when answer time is over limit`() {
        val soonQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isSoonQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = soonQueue,
            answerTime = MemorizationLevel.SOON.timeout
        )

        assertEquals(true, isQueueNotEmpty)
    }

    @Test
    fun `soonQueue is empty when answer time is not over limit`() {
        val soonQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
        val isQueueNotEmpty = isSoonQueueNotEmptyWhenAnswerTimeIsOverLimit(
            queue = soonQueue,
            answerTime = MemorizationLevel.NONE.timeout
        )

        assertEquals(false, isQueueNotEmpty)
    }


    private fun isImmediatelyQueueNotEmptyWhenAnswerTimeIsOverLimit(
        queue: Queue<MultiplicationExpressionHolder>,
        answerTime: Long
    ): Boolean {
        val currentExpression = MultiplicationExpression(multiplicand = 2, multiplier = 3)
        val expressionManager = MultiplicationExpressionManager(
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
        queue: Queue<MultiplicationExpressionHolder>,
        answerTime: Long
    ): Boolean {
        val currentExpression = MultiplicationExpression(multiplicand = 2, multiplier = 3)
        val expressionManager = MultiplicationExpressionManager(
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
        queue: Queue<MultiplicationExpressionHolder>,
        answerTime: Long
    ): Boolean {
        val currentExpression = MultiplicationExpression(multiplicand = 2, multiplier = 3)
        val expressionManager = MultiplicationExpressionManager(
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