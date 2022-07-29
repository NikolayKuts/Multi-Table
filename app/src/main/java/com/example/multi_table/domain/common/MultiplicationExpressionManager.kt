package com.example.multi_table.domain.common

import com.example.multi_table.domain.common.MemorizationLevel.*
import com.example.multi_table.domain.entities.MultiplicationExpression
import java.util.*

class MultiplicationExpressionManager {

    companion object {

        private val DIGIT_RANGE: IntRange = 2..9
    }

    private val noneQueue: Queue<MultiplicationExpression>
    private val soonQueue: Queue<MultiplicationExpression> = LinkedList()
    private val hurryQueue: Queue<MultiplicationExpression> = LinkedList()
    private val immediatelyQueue: Queue<MultiplicationExpression> = LinkedList()

    var currentExpression: MultiplicationExpression? = null
        private set

    init {
        noneQueue = getInitializedList()
    }

    fun nextExpression(
        answerTime: Long,
    ): MultiplicationExpression {
        val nextExpression = when {
            immediatelyQueue.isNotEmpty() -> immediatelyQueue.remove()
            hurryQueue.isNotEmpty() -> hurryQueue.remove()
            soonQueue.isNotEmpty() -> soonQueue.remove()
            else -> noneQueue.remove()
        }

        currentExpression?.let { orderExpression(expression = it, answerTime = answerTime) }
        currentExpression = nextExpression

        return nextExpression
    }

    fun nextExpressionAfterWrongOne(): MultiplicationExpression {
        val nextExpression = when {
            immediatelyQueue.isNotEmpty() -> immediatelyQueue.remove()
            hurryQueue.isNotEmpty() -> hurryQueue.remove()
            soonQueue.isNotEmpty() -> soonQueue.remove()
            else -> noneQueue.remove()
        }

        currentExpression?.let {
            orderExpression(expression = it, answerTime = IMMEDIATELY.timeout)
        }
        currentExpression = nextExpression

        return nextExpression
    }

    private fun getInitializedList(): LinkedList<MultiplicationExpression> {
        return LinkedList<MultiplicationExpression>().apply {
            (DIGIT_RANGE).onEach { multiplicand: Int ->
                (DIGIT_RANGE).onEach { multiplier: Int ->
                    add(
                        MultiplicationExpression(
                            multiplicand = multiplicand,
                            multiplier = multiplier
                        )
                    )
                }
            }
            shuffle()
        }
    }

    private fun orderExpression(expression: MultiplicationExpression, answerTime: Long) {
        when {
            answerTime >= IMMEDIATELY.timeout -> immediatelyQueue.add(expression)
            answerTime >= HURRY.timeout -> hurryQueue.add(expression)
            answerTime >= SOON.timeout -> soonQueue.add(expression)
            answerTime >= NONE.timeout -> noneQueue.add(expression)
        }
    }
}