package com.example.multi_table.domain

import com.example.multi_table.domain.MemorizationLevel.*
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
        val expressions = mutableListOf<MultiplicationExpression>()
        (DIGIT_RANGE).onEach { multiplicand: Int ->
            (DIGIT_RANGE).onEach { multiplier: Int ->
                expressions.add(
                    MultiplicationExpression(multiplicand = multiplicand, multiplier = multiplier)
                )
            }
        }
        expressions.shuffle()
        noneQueue = LinkedList(expressions)
    }

    fun nextExpression(
        answerTime: Long
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

    private fun orderExpression(expression: MultiplicationExpression, answerTime: Long) {
        when {
            answerTime >= IMMEDIATELY.timeout -> immediatelyQueue.add(expression)
            answerTime >= HURRY.timeout -> hurryQueue.add(expression)
            answerTime >= SOON.timeout -> soonQueue.add(expression)
            answerTime >= NONE.timeout -> noneQueue.add(expression)

        }
    }
}

enum class MemorizationLevel(val timeout: Long) {
    NONE(timeout = 1000L),
    SOON(timeout = 2000L),
    HURRY(timeout = 2500L),
    IMMEDIATELY(timeout = 3000L)
}