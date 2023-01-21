package com.example.multi_table.di

import com.example.multi_table.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PresentationModule = module {

    viewModel {
        MainViewModel(
            timer = get(),
            getMultiplicationExpressionWithoutResult = get(),
            getMultiplicationExpressionWithResult = get(),
        )
    }
}