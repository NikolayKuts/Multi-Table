package com.example.multi_table.domain.entities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class Timer {

    companion object {

        private const val TIMER_INITIAL_VALUE: Long = 0L
        private const val LATENCY: Long = 100L
    }

    private var isRunning = false
    private var isPaused = false

    private var runningJob: Job? = null

    private val _time = MutableStateFlow(value = TIMER_INITIAL_VALUE)
    val time = _time.asStateFlow()

    private val parsedTime = _time.map { parseTime(millis = it) }

    fun run(scope: CoroutineScope) {
        cancelRunning()

        runningJob = scope.launch {
            isRunning = true

            while (isRunning) {
                delay(LATENCY)
                _time.value += LATENCY
            }
        }
    }

    fun resume(scope: CoroutineScope) {
        if (isPaused) {
            runningJob = scope.launch {
                isPaused = false

                while(isRunning) {
                    delay(LATENCY)
                    _time.value += LATENCY
                }
            }
        }
    }

    fun pause() {
        isPaused = true
        runningJob?.cancel()
    }

    fun stop() {
        isRunning = false
        runningJob?.cancel()
    }

    fun getParsedTimeAsStateFlow(scope: CoroutineScope) = parsedTime.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = parseTime(millis = _time.value)
    )

    private fun parseTime(millis: Long): Time {
        return Time(
            seconds = TimeUnit.MILLISECONDS.toSeconds(millis).toInt(),
            millis = (millis % TimeUnit.SECONDS.toMillis(1) / LATENCY).toInt()
        )
    }

    private fun cancelRunning() {
        runningJob?.cancel()
        isRunning = false
        _time.value = TIMER_INITIAL_VALUE
    }

    class Time(
        val seconds: Int,
        val millis: Int
    )
}