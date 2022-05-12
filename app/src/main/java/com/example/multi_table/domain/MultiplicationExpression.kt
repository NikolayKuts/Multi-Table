package com.example.multi_table.domain

class MultiplicationExpression(
    val multiplicand: Int,
    val multiplier: Int,
) {

    val product: Int get() = multiplicand * multiplier
}