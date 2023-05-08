package com.kuts.domain.common

import com.kuts.domain.common.MemorizationLevel.*
import com.kuts.domain.entities.MultiplicationExpression
import com.kuts.domain.entities.MultiplicationExpressionHolder
import com.kuts.domain.entities.RepetitionState
import java.util.*

class MultiplicationExpressionManager(
    private val noneQueue: Queue<MultiplicationExpressionHolder> = getInitializedQueue(),
    private val soonQueue: Queue<MultiplicationExpressionHolder> = LinkedList(),
    private val hurryQueue: Queue<MultiplicationExpressionHolder> = LinkedList(),
    private val immediatelyQueue: Queue<MultiplicationExpressionHolder> = LinkedList(),
    private var counter: Int = INITIAL_COUNTER_VALUE,
    currentExpression: MultiplicationExpression? = null
) {

    companion object {

        private const val INITIAL_COUNTER_VALUE = 0
        private val DIGIT_RANGE: IntRange = 2..9
        private const val IMMEDIATELY_STEP = 2
        private const val HURRY_STEP = 3
        private const val SOON_STEP = 4

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
                                count = INITIAL_COUNTER_VALUE
                            )
                        )
                    }
                }
                shuffle()
            }
        }
    }

    var currentExpression: MultiplicationExpression? = currentExpression
        private set

    fun nextExpression(
        answerTime: Long,
        repetitionState: RepetitionState,
    ): MultiplicationExpression {
        currentExpression?.let {
            orderExpressions(
                holder = MultiplicationExpressionHolder(expression = it, count = counter),
                answerTime = if (repetitionState == RepetitionState.SUCCESS) answerTime else IMMEDIATELY.timeout
            )
        }

        val nextExpressionHolder = getNextExpressionHolder()
        currentExpression = nextExpressionHolder.expression
        counter++

        return nextExpressionHolder.expression
    }

    private fun getNextExpressionHolder(): MultiplicationExpressionHolder = when {
        immediatelyQueue.shouldBeGiven(step = IMMEDIATELY_STEP) -> immediatelyQueue.remove()
        hurryQueue.shouldBeGiven(step = HURRY_STEP) -> hurryQueue.remove()
        soonQueue.shouldBeGiven(step = SOON_STEP) -> soonQueue.remove()
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