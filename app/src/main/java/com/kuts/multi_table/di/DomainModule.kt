package com.kuts.multi_table.di

import com.kuts.domain.common.MultiplicationExpressionManager
import com.kuts.domain.common.Timer
import com.kuts.domain.useCases.GetMultiplicationExpressionWithResultUseCase
import com.kuts.domain.useCases.GetMultiplicationExpressionWithoutResultUseCase
import org.koin.dsl.module

val DomainModule = module {

    factory { Timer() }

    single { MultiplicationExpressionManager() }

    factory {
        GetMultiplicationExpressionWithoutResultUseCase(
            expressionManager = get()
        )
    }

    factory {
        GetMultiplicationExpressionWithResultUseCase(
            expressionManager = get()
        )
    }
}