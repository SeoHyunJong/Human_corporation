package com.example.humancorporation

data class CSStock(
    var createdAt: Long = 1,
    val open: Float = 3000f,
    val close: Float,
    val shadowHigh: Float,
    val shadowLow: Float
)