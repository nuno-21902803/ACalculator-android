package com.example.acalculator

import android.util.Log
import net.objecthunter.exp4j.ExpressionBuilder

abstract class CalculatorModel() {

    private val TAG = MainActivity::class.java.simpleName
    var expression: String = "0"
        private set

    fun insertSymbol(symbol: String): String {

        if (symbol == "C") {
            expression = "0"

        } else if (symbol == "B") {
            expression = if (expression.length > 1) {
                expression.subSequence(0, expression.length - 1) as String

            } else {
                "0"
            }

        } else {
            expression = if (expression == "0") {
                symbol
            } else {
                "$expression$symbol"
            }
        }
        Log.i(TAG, "CalculatorModel new symbol $symbol")

        return expression
    }

    open fun performOperation(onFinished: () -> Unit) {
        val expressionBuilder = ExpressionBuilder(expression).build()
        val expression = expression
        val result = expressionBuilder.evaluate()
        Log.i(TAG, "CalculatorModel performs operation -> $expression=$result")

        this.expression = result.toString()
        onFinished()
    }


    fun clearDisplay(): String {
        expression = "0"
        Log.i(TAG, "CalculatorModel cleared display")
        return expression
    }


    abstract fun insertOperations(operations: List<OperationUI>, onFinished: (List<OperationUI>) -> Unit)
    abstract fun getLastOperation(onFinished: (String) -> Unit)
    abstract fun deleteOperation(uuid: String, onFinished: () -> Unit)
    abstract fun deleteAllOperations(onFinished: () -> Unit)
    abstract fun getHistory(onFinished: (List<OperationUI>) -> Unit)
}