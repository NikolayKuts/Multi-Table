package com.example.multi_table.di

import com.example.multi_table.domain.MultiplicationExpressionManager
import com.example.multi_table.domain.entities.Timer
import com.example.multi_table.domain.entities.useCases.GetMultiplicationExpressionAfterWrongOneUseCase
import com.example.multi_table.domain.entities.useCases.GetMultiplicationExpressionWithResultUseCase
import com.example.multi_table.domain.entities.useCases.GetMultiplicationExpressionWithoutResultUseCase
import com.example.multi_table.presentation.MainActivity
import com.example.multi_table.presentation.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val DomainModule = module {

    factory { Timer() }

    single { MultiplicationExpressionManager() }

    factory { GetMultiplicationExpressionWithoutResultUseCase(expressionManager = get()) }

    factory { GetMultiplicationExpressionWithResultUseCase(expressionManager = get()) }

    factory { GetMultiplicationExpressionAfterWrongOneUseCase(expressionManager = get()) }
}