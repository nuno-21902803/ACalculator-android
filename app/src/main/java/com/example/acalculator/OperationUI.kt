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
    val timeStamp: Long = System.currentTimeMillis()
) : Parcelable {



    override fun toString(): String {
        return "OperationUI(expression='$expression', result='$result')"
    }

}