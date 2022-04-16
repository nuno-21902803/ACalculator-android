package com.example.acalculator

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorModel {

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
        val result = expressionBuilder.evaluate()

        history.add(Operation(display, result))
        display = result.toString()

        CoroutineScope(Dispatchers.IO).launch {
            addToHistory(display,result)
        }

        return result
    }

    suspend fun addToHistory(expression: String, result: Double){
        Thread.sleep(30*1000)
        history.add(Operation(display, result))

    }


    fun getAllOperations(callback:(List<Operation>) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(30*1000)
            //callback é como se fosse um return( mas temos que ter definido na funcao) ln.61
            callback(history.toList())
        }

    }


}