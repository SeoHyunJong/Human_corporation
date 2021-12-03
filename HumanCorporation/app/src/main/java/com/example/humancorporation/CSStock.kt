package com.example.humancorporation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CSStock")
data class CSStock(
    @PrimaryKey var date: Long = 0,
    @ColumnInfo(name = "open") val open: Float = 3000f,
    @ColumnInfo(name = "close") val close: Float,
    @ColumnInfo(name = "shadowHigh") val shadowHigh: Float,
    @ColumnInfo(name = "shadowLow") val shadowLow: Float
)