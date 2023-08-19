package com.example.calculatorcompose

sealed class CalculatorOperations(val symbol: String) {
    object Sum: CalculatorOperations("+")
    object Subtract: CalculatorOperations("-")
    object Multiply: CalculatorOperations("x")
    object Division: CalculatorOperations("/")
}
