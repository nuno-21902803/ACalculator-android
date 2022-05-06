package com.example.acalculator

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat


@Parcelize
class OperationUI(
    val uuid: String,
    val expression: String,
    val result: Double,
    private val timeStamp: Long = System.currentTimeMillis()
) : Parcelable {



    @SuppressLint("SimpleDateFormat")
    fun getOperationDate(): String {
        val format = SimpleDateFormat("dd-MM-yyyy' - 'HH:mm:ss")
        return format.format(timeStamp)
    }

    override fun toString(): String {
        return "OperationUI(expression='$expression', result='$result')"
    }

}