package com.example.miprimerapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    private val model = CalculatorModel()

    val result = mutableStateOf("0")

    val expression = mutableStateOf("")
    val nodes = mutableListOf<String>()
    private val negativeSign = "-"
    private val decimalSeparator = ","
    var lastIsOperator = false
    private var showingResult = false
    private val rules = RulesCalculator() //aqui llamames el doc aparte con las reglas
    val error = mutableStateOf(CalculatorError.None)

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
    multiplySymbol: String,
    divideSymbol: String,
    percentSymbol: String,
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
        calculateResult(addSymbol, subtractionSymbol, multiplySymbol, divideSymbol, percentSymbol)
        return
    }
    if (valueButton == subtractionSymbol) {
        addMinus(valueButton, addSymbol)
        return
    }
    if (valueButton == decimalSeparator) {
        addDecimalSeparator()
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
        if (showingResult) {
            nodes.clear()
            expression.value = ""
            showingResult = false
        }
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
        if (!rules.canAddOperator(nodes, lastIsOperator, negativeSign, decimalSeparator )) return
        showingResult = false
        if (lastIsOperator) {
            nodes[nodes.size - 1] = operatorSymbol
        } else {
            nodes.add(operatorSymbol)
        }
        lastIsOperator = true
        showNodes()
    }

    fun showNodes() {
        if (error.value != CalculatorError.None) {
            error.value = CalculatorError.None
            expression.value = ""
        }
        if (nodes.isEmpty()) {
            result.value = "0"
        } else {
            result.value = nodes.joinToString("")
        }
    }
    fun clearFunction() {
        nodes.clear()
        lastIsOperator = false
        showingResult = false
        expression.value = ""
        result.value = "0"
        error.value = CalculatorError.None
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

    fun addDecimalSeparator() {
        if (!rules.canAddDecimalSeparator(nodes, lastIsOperator, decimalSeparator)) return

        val lastIndex = nodes.size - 1
        nodes[lastIndex] = nodes[lastIndex] + decimalSeparator
        showNodes()
    }
    fun calculateResult(
        addSymbol: String,
        subtractionSymbol: String,
        multiplySymbol: String,
        divideSymbol: String,
        percentSymbol: String
    ) {
        if (!rules.canCalculate(nodes, lastIsOperator)) return

        val items = mutableListOf<String>()
        items.addAll(nodes)

        solveFirstPriority(items, multiplySymbol, divideSymbol, percentSymbol)

        if (error.value != CalculatorError.None) {
            expression.value = nodes.joinToString("")
            nodes.clear()
            lastIsOperator = false
            return
        }
        solveSecondPriority(items, addSymbol)
        val finalText = resultHistory(items[0].toDouble())
        expression.value = nodes.joinToString("")
        result.value = finalText

        nodes.clear()
        nodes.add(finalText)
        lastIsOperator = false
        showingResult = true
    }

    fun solveFirstPriority(
        items: MutableList<String>,
        multiplySymbol: String,
        divideSymbol: String,
        percentSymbol: String
    ) {
        var index = 1
        while (index < items.size) {
            val operator = items[index]
            val isFirstPriority = operator == multiplySymbol || operator == divideSymbol || operator == percentSymbol

            if (isFirstPriority) {
                val number1 = items[index - 1].replace(decimalSeparator, ".").toDouble()
                val number2 = items[index + 1].replace(decimalSeparator, ".").toDouble()

                if ((operator == divideSymbol || operator == percentSymbol) && number2 == 0.0) {
                    error.value = CalculatorError.DivisionByZero
                    return
                }
                var partialResult = 0.0

                if (operator == multiplySymbol) {
                    partialResult = model.multiply(number1, number2)
                }
                if (operator == divideSymbol) {
                    partialResult = model.divide(number1, number2)
                }
                if (operator == percentSymbol) {
                    partialResult = model.module(number1, number2)
                }

                items[index - 1] = partialResult.toString()
                items.removeAt(index)
                items.removeAt(index)
            } else {
                index += 2
            }
        }

    }


    fun solveSecondPriority(
        items: MutableList<String>,
        addSymbol: String
    ) {
        while (items.size > 1) {
            val number1 = items[0].replace(decimalSeparator, ".").toDouble()
            val operator = items[1]
            val number2 = items[2].replace(decimalSeparator, ".").toDouble()
            var partialResult = 0.0

            if (operator == addSymbol) {
                partialResult = model.add(number1, number2)
            } else {
                partialResult = model.subtraction(number1, number2)
            }

            items[0] = partialResult.toString()
            items.removeAt(1)
            items.removeAt(1)
        }
    }
    fun resultHistory(number: Double): String {
        var text = number.toString()
        if (text.endsWith(".0")) {
            text = text.removeSuffix(".0")
        }
        return text.replace(".", decimalSeparator)
    }
}