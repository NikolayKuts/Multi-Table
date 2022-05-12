package com.example.multi_table.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Timer(private val coroutineScope: CoroutineScope) {

    private var isRunning = false
    private var runningJob: Job? = null

    private val _time = MutableStateFlow(value = 0)
    val time = _time.asStateFlow()

    val parsedTime = _time.map { parseTime(millis = it) }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = parseTime(millis = _time.value)
    )

    fun run() {
        isRunning = true
        _time.value = 0

        runningJob = coroutineScope.launch {
            while (isRunning) {
                delay(100)
                _time.value += 100
            }
        }
    }

    fun pause() {
        isRunning = false
        runningJob?.cancel()
    }

    private fun parseTime(millis: Int): Time {
        return Time(seconds = millis / 1000, millis = (millis % 1000) / 100)
    }

    class Time(
        val seconds: Int,
        val millis: Int
    )
}