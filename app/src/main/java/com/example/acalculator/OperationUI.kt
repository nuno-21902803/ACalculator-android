package com.example.acalculator

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat


@Parcelize
class OperationUI(
    val expression: String,
    val result: String,
    private val timeStamp: Long = System.currentTimeMillis()
) : Parcelable {



    @SuppressLint("SimpleDateFormat")
    fun getOperationDate(): String {
        val format = SimpleDateFormat("dd-MM-yyyy' - T'HH:mm:ss")
        return format.format(timeStamp)
    }

    override fun toString(): String {
        return "OperationUI(expression='$expression', result='$result')"
    }

}