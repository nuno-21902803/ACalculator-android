package com.example.acalculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorRepository.getInstance()


    private val TAG = MainActivity::class.java.simpleName

    fun getDisplayValue() = model.getExpression()

    fun onClickSymbol(symbol: String): String {
        Log.i(TAG, "Click \'$symbol\'")
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(onSaved: () -> Unit): String {
        model.performOperation(onSaved)
        val result = getDisplayValue().toDouble()
        return if (result % 1 == 0.0) result.toLong().toString() else result.toString()
    }

    fun getHistory(onFinished: (List<OperationUI>) -> Unit) {
        Log.i(TAG, "CalculatorViewModel get history")
        CoroutineScope(Dispatchers.Main).launch {
            model.getHistory(onFinished)
        }
    }

    fun onClickClear(): String {
        Log.i(TAG, "Click \'C\'")
        return model.insertSymbol("C")
    }


    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        model.deleteOperation(uuid, onSuccess)
    }


}