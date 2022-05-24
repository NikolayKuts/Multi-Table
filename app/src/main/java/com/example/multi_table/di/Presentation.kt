package com.example.multi_table.di

import com.example.multi_table.domain.MultiplicationExpressionManager
import com.example.multi_table.domain.Timer
import com.example.multi_table.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        MainViewModel(expressionManager = MultiplicationExpressionManager(), timer = Timer())
    }
}