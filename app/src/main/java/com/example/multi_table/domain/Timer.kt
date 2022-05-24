package com.example.multi_table.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Timer {

    private var isRunning = false
    private var runningJob: Job? = null

    private val _time = MutableStateFlow(value = 0)
    val time = _time.asStateFlow()

    private val parsedTime = _time.map { parseTime(millis = it) }
    fun run(scope: CoroutineScope) {
        cancelRunning()

        runningJob = scope.launch {
            isRunning = true

            while (isRunning) {
                delay(100)
                _time.value += 100
            }
        }
    }

    fun getParsedTimeAsStateFlow(scope: CoroutineScope) = parsedTime.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = parseTime(millis = _time.value)
    )

    fun stop() {
        isRunning = false
        runningJob?.cancel()
    }

    private fun parseTime(millis: Int): Time {
        return Time(seconds = millis / 1000, millis = (millis % 1000) / 100)
    }

    private fun cancelRunning() {
        runningJob?.cancel()
        isRunning = false
        _time.value = 0
    }

    class Time(
        val seconds: Int,
        val millis: Int
    )
}