package com.kuts.multi_table.di

import com.kuts.presentation.MainViewModel
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