package com.example.multi_table.domain.common

enum class MemorizationLevel(val timeout: Long) {
    NONE(timeout = 1000L),
    SOON(timeout = 2000L),
    HURRY(timeout = 2500L),
    IMMEDIATELY(timeout = 3000L)
}