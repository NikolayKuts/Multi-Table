package com.kuts.presentation

sealed class MainEvent {

    object NextExpression : MainEvent()
    object ExpressionResult : MainEvent()
    object WrongAnswer : MainEvent()
}