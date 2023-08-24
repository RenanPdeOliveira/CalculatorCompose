package com.example.calculatorcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())

    fun onAction(calculatorActions: CalculatorActions) {
        when (calculatorActions) {
            is CalculatorActions.Number -> enterNumber(calculatorActions.number)
            is CalculatorActions.Clear -> clearButton()
            is CalculatorActions.Back -> backButton()
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Calculate -> calculateResult()
            is CalculatorActions.Operations -> enterOperation(calculatorActions.operation)
        }
    }

    private fun enterNumber(num: Int) {
        if (state.operation == null) {
            if (state.number1.length >= MAX_LENGTH) {
                return
            }
            if (state.number1 == "0") {
                state = state.copy(number1 = state.number1.dropLast(1))
                state = state.copy(number1 = state.number1 + num)
                return
            }
            state = state.copy(number1 = state.number1 + num)
            return
        }
        if (state.number2.length >= MAX_LENGTH) {
            return
        }
        state = state.copy(number2 = state.number2 + num)
    }

    private fun clearButton() {
        state = state.copy(number1 = "0", number2 = "", operation = null)
    }

    private fun backButton() {
        when {
            state.number2.isNotBlank() -> {
                state = state.copy(number2 = state.number2.dropLast(1))
            }
            state.operation != null -> {
               state = state.copy(operation = null)
            }
            state.number1.isNotBlank() && state.number1.length >= 2 -> {
                state = state.copy(number1 = state.number1.dropLast(1))
            }
            state.number1.isNotBlank() && state.number1.length == 1 -> {
                clearButton()
            }
        }
    }

    private fun enterDecimal() {
        if (state.operation == null &&
            !state.number1.contains(".") &&
            state.number1.isNotBlank()
        ) {
            state = state.copy(number1 = state.number1 + ".")
            return
        }
        if (!state.number2.contains(".") &&
            state.number2.isNotBlank()
        ) {
            state = state.copy(number2 = state.number2 + ".")
        }
    }

    private fun calculateResult() {
        val num1 = state.number1.toDoubleOrNull()
        val num2 = state.number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = when(state.operation) {
                is CalculatorOperations.Sum -> num1 + num2
                is CalculatorOperations.Subtract -> num1 - num2
                is CalculatorOperations.Division -> num1 / num2
                is CalculatorOperations.Multiply -> num1 * num2
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(10),
                number2 = "",
                operation = null
            )
        }
    }

    private fun enterOperation(operation: CalculatorOperations) {
        if (state.number1.isNotBlank() && state.number1 != "0") {
            state = state.copy(operation = operation)
        }
    }

    companion object {
        private const val MAX_LENGTH = 8
    }
}