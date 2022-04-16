package com.example.acalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorModel()

    fun getDisplayValue() = model.display

    fun onClickSymbol(symbol: String): String {
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(): String {
        return model.performOperation().toString()
    }

    fun getHistory(callback: (List<OperationUI>) -> Unit) {
        model.getAllOperations { operations ->
            val history = operations.map {
                OperationUI(it.expression, it.result, it.timestamp)
            }
            CoroutineScope(Dispatchers.Main).launch { callback(history) }

        }

    }

}