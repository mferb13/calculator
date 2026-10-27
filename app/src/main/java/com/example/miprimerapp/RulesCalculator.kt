package com.example.miprimerapp

class RulesCalculator {

    fun canAddOperator(nodes: List<String>, lastIsOperator: Boolean, negativeSign: String): Boolean {
        if (nodes.isEmpty()) {
            return false
        }
        if (!lastIsOperator && nodes.last() == negativeSign) {
            return false
        }
        return true
    }
}