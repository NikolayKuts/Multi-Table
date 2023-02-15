package com.example.multi_table.di

import com.example.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PresentationModule = module {

    viewModel {
        com.example.presentation.MainViewModel(
            timer = get(),
            getMultiplicationExpressionWithoutResult = get(),
            getMultiplicationExpressionWithResult = get(),
        )
    }
}