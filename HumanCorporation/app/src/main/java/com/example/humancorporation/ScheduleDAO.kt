package com.example.humancorporation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/*
무슨 쿼리가 필요할까?
1. 다 내놔
"SELECT * FROM Schedule"
2. 특정 날짜에 해당하는 스케쥴 다 내놔
"SELECT * FROM Schedule WHERE date ='2021.12.19'"
3. 중복 검사 A
"SELECT * FROM Schedule WHERE date = '2021.12.19' AND startTime BETWEEN 8 AND 10"
4. 중복 검사 B
"SELECT * FROM Schedule WHERE date = '2021.12.19' AND endTime BETWEEN 8 AND 10"
5. 중복 검사 C
"SELECT * FROM Schedule WHERE date = '2021.12.19' AND startTime <= 8 AND endTime >= 10"
 */
@Dao
interface ScheduleDAO{
    @Query("SELECT * FROM Schedule WHERE date = :d")
    fun getAll(d: Long): List<Schedule>

    @Query("SELECT * FROM Schedule WHERE date = :d AND startTime BETWEEN :s AND :e")
    fun inspectionA(d: Long, s: Int, e: Int): List<Schedule>

    @Query("SELECT * FROM Schedule WHERE date = :d AND endTime BETWEEN :s AND :e")
    fun inspectionB(d: Long, s: Int, e: Int): List<Schedule>

    @Query("SELECT * FROM Schedule WHERE date = :d AND startTime <= :s AND endTime >= :e")
    fun inspectionC(d: Long, s: Int, e: Int): List<Schedule>

    @Insert
    fun insert(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Query("DELETE FROM Schedule WHERE date = :d")
    fun deleteAll(d: Long)

    @Query("SELECT price FROM Schedule WHERE date < :d ORDER BY id DESC LIMIT 1")
    fun openPrice(d: Long): Double

    @Query("SELECT max(price) FROM Schedule WHERE date = :d")
    fun maxPrice(d: Long): Double

    @Query("SELECT min(price) FROM Schedule WHERE date = :d")
    fun minPrice(d: Long): Double

    @Query("SELECT price FROM Schedule WHERE date = :d ORDER BY id DESC LIMIT 1")
    fun closedPrice(d: Long): Double

    @Query("SELECT DISTINCT date FROM Schedule ORDER BY date")
    fun getDate(): List<Long>
}