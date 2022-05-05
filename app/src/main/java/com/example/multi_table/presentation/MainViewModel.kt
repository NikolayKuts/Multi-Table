package com.example.multi_table.presentation

import androidx.lifecycle.ViewModel
import com.example.multi_table.presentation.MainState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow<MainState>(StartState)
    val state = _state.asStateFlow()

    fun sendEvent(event: MainEvent) {

    }
}