package com.example.calculatorcompose

data class CalculatorState(
    val number1: String = "0",
    val number2: String = "",
    val operation: CalculatorOperations? = null
)