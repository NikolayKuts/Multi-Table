package com.example.multi_table.presentation

sealed class MainEvent {

    object NextExpression : MainEvent()
    object ExpressionResult : MainEvent()
    object WrongAnswer : MainEvent()
}