package com.example.acalculator

import android.annotation.SuppressLint
import android.content.Context

class CalculatorRepository private constructor(
    private val context: Context,
    private val local: CalculatorModel, private val remote: CalculatorModel
) {

    fun getExpression() = local.expression

    fun insertSymbol(symbol: String): String {
        return remote.insertSymbol(symbol)
    }

    fun clear(): String {
        return remote.clearDisplay()
    }

    fun deleteLastSymbol(): String {
        return remote.insertSymbol("C")
    }

    fun performOperation(onFinished: () -> Unit) {
        remote.performOperation {
            local.expression = remote.expression
            onFinished()
        }
    }

    fun getLastOperation(onFinished: (String) -> Unit) {
        if (ConnectivityUtil.isOnline(context)) {
            remote.getLastOperation(onFinished)
        } else {
            local.getLastOperation(onFinished)
        }
    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        remote.deleteOperation(uuid, onSuccess)
    }

    fun getHistory(onFinished: (List<OperationUI>) -> Unit) {
        if (ConnectivityUtil.isOnline(context)) {
            remote.getHistory { history ->
                local.deleteAllOperations {
                    local.insertOperations(history) {
                        onFinished(history)
                    }
                }
            }
        } else {
            local.getHistory(onFinished)
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: CalculatorRepository? = null

        fun init(context: Context, local: CalculatorModel, remote: CalculatorModel) {
            synchronized(this) {
                if (instance == null) {
                    instance = CalculatorRepository(context, local, remote)
                }
            }
        }

        fun getInstance(): CalculatorRepository {
            return instance ?: throw IllegalStateException("Repository not initialized")
        }

    }
}
