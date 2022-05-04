package com.example.acalculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application):
AndroidViewModel(application){

    private val model = CalculatorModel(
        CalculatorDatabase.getInstance(application).operationDao()
    )

    private val TAG = MainActivity::class.java.simpleName

    fun getDisplayValue() = model.display

    fun onClickSymbol(symbol: String): String {
        Log.i(TAG, "Click \'$symbol\'")
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(onSaved: () -> Unit): String {
        model.performOperation(onSaved)
        val result = getDisplayValue().toDouble()
        return if(result % 1 == 0.0) result.toLong().toString() else result.toString()
    }

    fun getHistory(onFinished:  (List<OperationUI>) -> Unit) {
        Log.i(TAG, "CalculatorViewModel get history")
        CoroutineScope(Dispatchers.Main).launch{
            model.getAllOperations(onFinished)
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