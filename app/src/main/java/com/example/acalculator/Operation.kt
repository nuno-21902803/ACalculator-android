package com.example.acalculator

import java.util.*

data class Operation(
    val uuid: String = UUID.randomUUID().toString(),
    val expression: String,
    val result: Double
) {

    val timestamp: Long = Date().time


}