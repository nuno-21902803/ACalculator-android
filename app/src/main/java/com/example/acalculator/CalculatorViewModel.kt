package com.example.acalculator

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {
    private val model = CalculatorModel
    private val TAG = MainActivity::class.java.simpleName

    fun getDisplayValue() = model.display

    fun onClickSymbol(symbol: String): String {
        Log.i(TAG, "Click \'$symbol\'")
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(): String {
        Log.i(TAG, "Click \'=\'")
        val result = model.performOperation()
        return result.toString()
    }

    fun getHistory(callback: (List<OperationUI>) -> Unit) {
        Log.i(TAG, "CalculatorViewModel get history")
        model.getAllOperations { operations ->
            val history =
                operations.map {
                    OperationUI(it.uuid, it.expression, it.result, it.timestamp)
                }
            CoroutineScope(Dispatchers.Main).launch {
                callback(history)
                Log.wtf(TAG, history.toString())
            }
        }
    }

    fun onClickClear(): String {
        Log.i(TAG, "Click \'C\'")
        return model.clearDisplay()
    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        model.deleteOperation(uuid, onSuccess)
    }
}