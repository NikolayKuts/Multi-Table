package com.example.domain.entities

class MultiplicationExpression(
    val multiplicand: Int,
    val multiplier: Int,
) {

    val product: Int get() = multiplicand * multiplier
}