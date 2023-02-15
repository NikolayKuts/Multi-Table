package com.example.presentation

sealed class MainEvent {

    object NextExpression : MainEvent()
    object ExpressionResult : MainEvent()
    object WrongAnswer : MainEvent()
}