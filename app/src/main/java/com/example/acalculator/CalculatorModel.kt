package com.example.acalculator

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

object CalculatorModel {

    private val TAG = MainActivity::class.java.simpleName
    var display: String = "0"
    private val history = mutableListOf<Operation>()

    fun insertSymbol(symbol: String): String {

        if (symbol == "C") {
            display = "0"

        } else if (symbol == "B") {
            display = if (display.length > 1) {
                display.subSequence(0, display.length - 1) as String

            } else {
                "0"
            }

        } else {
            display = if (display == "0") {
                symbol
            } else {
                "$display$symbol"
            }
        }
        Log.i(TAG, "CalculatorModel new symbol $symbol")

        return display
    }

    fun performOperation(): Double {
        val expressionBuilder = ExpressionBuilder(display).build()
        val expression = display
        val result = expressionBuilder.evaluate()
        Log.i(TAG, "CalculatorModel performs operation -> $expression=$result")
        CoroutineScope(Dispatchers.IO).launch {
            addToHistory(expression, result)
        }
        display = result.toString()
        return result
    }

    suspend fun addToHistory(expression: String, result: Double) {
       ///////Thread.sleep(15 * 1000)
        history.add(Operation(expression = expression, result = result))
        Log.i(TAG, "CalculatorModel added $expression=$result to history")
    }


    fun getAllOperations(callback: (List<Operation>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            //Thread.sleep(30 * 1000)
            //callback é como se fosse um return( mas temos que ter definido na funcao) ln.61
            callback(history.toList())
        }

    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            //Thread.sleep(10 * 1000)
            val operation = history.find { it.uuid == uuid }
            history.remove(operation)
            onSuccess()
        }
    }

    fun clearDisplay(): String {
        display = "0"
        Log.i(TAG, "CalculatorModel cleared display")
        return display
    }
}