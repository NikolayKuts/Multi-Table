package com.example.multi_table.di

import com.example.multi_table.domain.common.MultiplicationExpressionManager
import com.example.multi_table.domain.common.Timer
import com.example.multi_table.domain.useCases.GetMultiplicationExpressionWithResultUseCase
import com.example.multi_table.domain.useCases.GetMultiplicationExpressionWithoutResultUseCase
import org.koin.dsl.module

val DomainModule = module {

    factory { Timer() }

    single { MultiplicationExpressionManager() }

    factory { GetMultiplicationExpressionWithoutResultUseCase(expressionManager = get()) }

    factory { GetMultiplicationExpressionWithResultUseCase(expressionManager = get()) }
}