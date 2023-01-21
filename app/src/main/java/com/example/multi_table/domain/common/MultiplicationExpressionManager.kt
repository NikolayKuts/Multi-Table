package com.example.multi_table.domain.common

import com.example.multi_table.domain.common.MemorizationLevel.*
import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.domain.entities.MultiplicationExpressionHolder
import com.example.multi_table.domain.entities.RepetitionState
import com.example.multi_table.domain.entities.RepetitionState.SUCCESS
import java.util.*

class MultiplicationExpressionManager {

    companion object {

        private val DIGIT_RANGE: IntRange = 2..9
    }

    private val noneQueue: Queue<MultiplicationExpressionHolder> = getInitializedQueue()
    private val soonQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
    private val hurryQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
    private val immediatelyQueue: Queue<MultiplicationExpressionHolder> = LinkedList()
    private var counter: Int = 0

    var currentExpression: MultiplicationExpression? = null
        private set

    fun nextExpression(
        answerTime: Long,
        repetitionState: RepetitionState,
    ): MultiplicationExpression {
        currentExpression?.let {
            orderExpressions(
                holder = MultiplicationExpressionHolder(expression = it, count = counter),
                answerTime = if (repetitionState == SUCCESS) answerTime else IMMEDIATELY.timeout
            )
        }

        val nextExpressionHolder = getNextExpressionHolder()
        currentExpression = nextExpressionHolder.expression
        counter++

        return nextExpressionHolder.expression
    }

    private fun getInitializedQueue(): LinkedList<MultiplicationExpressionHolder> {
        return LinkedList<MultiplicationExpressionHolder>().apply {
            (DIGIT_RANGE).onEach { multiplicand: Int ->
                (DIGIT_RANGE).onEach { multiplier: Int ->
                    add(
                        MultiplicationExpressionHolder(
                            expression = MultiplicationExpression(
                                multiplicand = multiplicand,
                                multiplier = multiplier
                            ),
                            count = counter
                        )
                    )
                }
            }
            shuffle()
        }
    }

    private fun getNextExpressionHolder(): MultiplicationExpressionHolder = when {
        immediatelyQueue.shouldBeGiven(step = 2) -> immediatelyQueue.remove()
        hurryQueue.shouldBeGiven(step = 3) -> hurryQueue.remove()
        soonQueue.shouldBeGiven(step = 4) -> soonQueue.remove()
        else -> noneQueue.remove()
    }

    private fun Queue<MultiplicationExpressionHolder>.shouldBeGiven(step: Int): Boolean {
        return isNotEmpty() && first().count < (counter - step)
    }

    private fun orderExpressions(holder: MultiplicationExpressionHolder, answerTime: Long) {
        when {
            answerTime >= IMMEDIATELY.timeout -> immediatelyQueue.add(holder)
            answerTime >= HURRY.timeout -> hurryQueue.add(holder)
            answerTime >= SOON.timeout -> soonQueue.add(holder)
            else -> noneQueue.add(holder)
        }
    }
}