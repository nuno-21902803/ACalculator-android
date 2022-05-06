package com.example.acalculator

import android.media.VolumeShaper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*

class CalculatorModel(private val dao: OperationDao) {

    private val TAG = MainActivity::class.java.simpleName
    var display: String = "0"
        private set

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

    fun performOperation(onFinished: () -> Unit): Double {
        val expressionBuilder = ExpressionBuilder(display).build()
        val expression = display
        val result = expressionBuilder.evaluate()
        Log.i(TAG, "CalculatorModel performs operation -> $expression=$result")

        val operation = OperationRoom(
            expression = expression, result = result, timestamp = Date().time
        )
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(operation = operation)
            onFinished()
        }
        display = result.toString()
        return result
    }


    fun getAllOperations(onFinished: (List<OperationUI>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            //Thread.sleep(30 * 1000)
            val operations = dao.getAll()
            onFinished(operations.map {
                OperationUI(it.uuid, it.expression, it.result, it.timestamp)
            })
        }
    }


    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteById(uuid)
            onSuccess()
        }
    }

    fun clearDisplay(): String {
        display = "0"
        Log.i(TAG, "CalculatorModel cleared display")
        return display
    }
}