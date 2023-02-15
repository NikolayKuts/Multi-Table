package com.example.multi_table.di

import com.example.domain.common.MultiplicationExpressionManager
import com.example.domain.common.Timer
import com.example.domain.useCases.GetMultiplicationExpressionWithResultUseCase
import com.example.domain.useCases.GetMultiplicationExpressionWithoutResultUseCase
import org.koin.dsl.module

val DomainModule = module {

    factory { com.example.domain.common.Timer() }

    single { com.example.domain.common.MultiplicationExpressionManager() }

    factory {
        com.example.domain.useCases.GetMultiplicationExpressionWithoutResultUseCase(
            expressionManager = get()
        )
    }

    factory {
        com.example.domain.useCases.GetMultiplicationExpressionWithResultUseCase(
            expressionManager = get()
        )
    }
}