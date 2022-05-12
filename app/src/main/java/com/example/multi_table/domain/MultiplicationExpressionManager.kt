package com.example.multi_table.domain

import com.example.multi_table.domain.MemorizationLevel.*
import java.util.*

class MultiplicationExpressionManager {

    private val noneQueue: Queue<MultiplicationExpression>
    private val soonQueue: Queue<MultiplicationExpression> = LinkedList()
    private val hurryQueue: Queue<MultiplicationExpression> = LinkedList()
    private val immediatelyQueue: Queue<MultiplicationExpression> = LinkedList()

    var currentExpression: MultiplicationExpression? = null
    private set

    init {
        val expressions = mutableListOf<MultiplicationExpression>()
        (2..9).onEach { multiplicand: Int ->
            (2..9).onEach { multiplier: Int ->
                expressions.add(
                    MultiplicationExpression(multiplicand = multiplicand, multiplier = multiplier)
                )
            }
        }
        expressions.shuffle()
        noneQueue = LinkedList(expressions)
    }

    fun nextExpression(
        answerTime: Int
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

        currentExpression?.let { orderExpression(expression = it, answerTime = IMMEDIATELY.timeout + 1) }
        currentExpression = nextExpression

        return nextExpression
    }

    private fun orderExpression(expression: MultiplicationExpression, answerTime: Int) {
        when {
            answerTime >= IMMEDIATELY.timeout -> immediatelyQueue.add(expression)
            answerTime >= HURRY.timeout -> hurryQueue.add(expression)
            answerTime >= SOON.timeout -> soonQueue.add(expression)
            answerTime >= NONE.timeout -> noneQueue.add(expression)

        }
    }
}

enum class MemorizationLevel(val timeout: Int) {
    NONE(timeout = 1000),
    SOON(timeout = 1500),
    HURRY(timeout = 2000),
    IMMEDIATELY(timeout = 2500)
}