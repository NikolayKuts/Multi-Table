package com.kuts.presentation

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectWhenStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline onEach: (T) -> Unit,
): Job {
    return lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect { element -> onEach(element) }
        }
    }
}

fun <T> log(
    message: T,
    pointerMessage: String = "",
    tag: String = "app_log",
    pointer: String =
        if (pointerMessage.isEmpty()) "***********" else "****** $pointerMessage ******",
) {
    Log.i(tag, "$pointer $message")
}