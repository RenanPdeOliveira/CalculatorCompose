package com.example.calculatorcompose

sealed class CalculatorActions {
    data class Number(val number: String): CalculatorActions()
    object Clear: CalculatorActions()
    object Back: CalculatorActions()
    object Decimal: CalculatorActions()
    object Calculate: CalculatorActions()
    data class Operations(val operation: CalculatorOperations): CalculatorActions()
}
