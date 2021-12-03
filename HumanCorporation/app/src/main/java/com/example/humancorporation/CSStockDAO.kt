package com.example.humancorporation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CSStockDAO {
    @Query("SELECT * FROM CSStock")
    fun getAll(): List<CSStock>

    @Query("SELECT close FROM CSStock WHERE date < :d ORDER BY date DESC LIMIT 1")
    fun getOpenPrice(d: Long): Float

    @Query("SELECT close FROM CSStock ORDER BY date DESC LIMIT 1")
    fun getTodayPrice(): Float

    @Query("SELECT open FROM CSStock ORDER BY date DESC LIMIT 1")
    fun getTodayOpenPrice(): Float

    @Insert
    fun insert(stock: CSStock)

    @Delete
    fun delete(stock: CSStock)

    @Query("DELETE FROM CSStock WHERE date = :d")
    fun deleteAll(d: Long)

    @Query("SELECT max(shadowHigh) FROM CSStock")
    fun getMaxPrice(): Float

    @Query("SELECT min(shadowLow) FROM CSStock")
    fun getMinPrice(): Float
}