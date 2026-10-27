package com.example.miprimerapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    private val model = CalculatorModel()

    val result = mutableStateOf("0")

    val expression = mutableStateOf("")
    val nodes = mutableListOf<String>()
    private val negativeSign = "-"
    var lastIsOperator = false
    private val rules = RulesCalculator() //aqui llamames el doc aparte con las reglas

// Para agregar cada funcion de un operador se debe mantener el orden
    fun manageClickButtons(
    valueButton: String,
    equalSymbol: String,
    msgError: String,
    clearSymbol: String,
    backspaceSymbol: String,
    changeSymbol: String,
    subtractionSymbol: String,
    addSymbol: String,
    isOperator: Boolean
    ) {
        if (valueButton.isEmpty()) return

        if (valueButton == clearSymbol) {
            clearFunction()
            return
        }

    if (valueButton == backspaceSymbol) {
        backspaceSymbolfunction()
        return
    }

    if (valueButton == changeSymbol) {
        changeSymbolfunction()
        return
    }

    if (valueButton == equalSymbol) {
            return
        }
    if (valueButton == subtractionSymbol) {
        addMinus(valueButton, addSymbol)
        return
    }

        if (isOperator) {
            addOperator(valueButton)
        } else {
            addDigit(valueButton)
        }
    }

    fun add(number1: String, number2: String, msgError: String): String {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()

        if (num1 != null && num2 != null) {
            return model.add(num1, num2).toString()
        } else {
            return msgError
        }
    }
    fun addDigit(digit: String) {
        if (nodes.isEmpty() || lastIsOperator) {
            nodes.add(digit)
        } else {
            val lastIndex = nodes.size - 1
            nodes[lastIndex] = nodes[lastIndex] + digit
        }
        lastIsOperator = false
        showNodes()
    }

    fun addOperator(operatorSymbol: String) {
        if (!rules.canAddOperator(nodes, lastIsOperator, negativeSign)) return
        if (lastIsOperator) {
            nodes[nodes.size - 1] = operatorSymbol
        } else {
            nodes.add(operatorSymbol)
        }
        lastIsOperator = true
        showNodes()
    }

    fun showNodes() {
        if (nodes.isEmpty()) {
            result.value = "0"
        } else {
            result.value = nodes.joinToString("")
        }
    }
    fun clearFunction() {
        nodes.clear()
        lastIsOperator = false
        result.value = "0"
    }

    fun backspaceSymbolfunction() {
        if (nodes.isEmpty()) return

        val lastIndex = nodes.size - 1
        val shortenedText = nodes[lastIndex].dropLast(1)

        if (shortenedText.isEmpty()) {
            nodes.removeAt(lastIndex)
            lastIsOperator = !lastIsOperator
        } else {
            nodes[lastIndex] = shortenedText
        }
        showNodes()
    }

    fun changeSymbolfunction() {
        if (nodes.isEmpty() || lastIsOperator) return
        if (nodes.last() == negativeSign) return

        val lastIndex = nodes.size - 1
        val lastNode = nodes[lastIndex]

        if (lastNode.startsWith(negativeSign)) {
            nodes[lastIndex] = lastNode.removePrefix(negativeSign)
        } else {
            nodes[lastIndex] = negativeSign + lastNode
        }
        showNodes()
    }

    fun addMinus(minusSymbol: String, addSymbol: String) {
        if (nodes.isEmpty()) {
            nodes.add(minusSymbol)
            lastIsOperator = false
            showNodes()
            return
        }

        if (!lastIsOperator && nodes.last() == minusSymbol) return

        if (!lastIsOperator) {
            addOperator(minusSymbol)
            return
        }

        val lastOperator = nodes.last()
        if (lastOperator == addSymbol || lastOperator == minusSymbol) {
            nodes[nodes.size - 1] = minusSymbol
        } else {
            nodes.add(minusSymbol)
            lastIsOperator = false
        }
        showNodes()
    }
    fun subtraction(number1: String, number2: String,msgError: String): String{
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.subtraction(num1, num2).toString()
            return result
        } else {
            return msgError
        }
    }

}