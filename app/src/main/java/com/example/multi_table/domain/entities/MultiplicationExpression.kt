package com.example.multi_table.domain.entities

class MultiplicationExpression(
    val multiplicand: Int,
    val multiplier: Int,
) {

    val product: Int get() = multiplicand * multiplier

    override fun toString(): String {
        return "<$multiplicand * $multiplier>"
    }
}