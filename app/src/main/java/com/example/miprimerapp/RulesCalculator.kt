package com.example.miprimerapp

class RulesCalculator {

    fun canAddOperator(nodes: List<String>, lastIsOperator: Boolean, negativeSign: String, decimalSeparator: String): Boolean {
        if (nodes.isEmpty()) {
            return false
        }
        if (!lastIsOperator && nodes.last() == negativeSign) {
            return false
        }
        if (!lastIsOperator && nodes.last().endsWith(decimalSeparator)) {
            return false
        }
        return true
    }
    fun canCalculate(nodes: List<String>, lastIsOperator: Boolean): Boolean {
        if (nodes.isEmpty()) {
            return false
        }
        if (lastIsOperator) {
            return false
        }
        if (!nodes.last().last().isDigit()) {
            return false
        }
        return true
    }
    fun canAddDecimalSeparator(nodes: List<String>, lastIsOperator: Boolean, decimalSeparator: String): Boolean {
        if (nodes.isEmpty()) {
            return false
        }
        if (lastIsOperator) {
            return false
        }
        val lastNode = nodes.last()
        if (lastNode.contains(decimalSeparator)) {
            return false
        }
        if (!lastNode.last().isDigit()) {
            return false
        }
        return true
    }
}