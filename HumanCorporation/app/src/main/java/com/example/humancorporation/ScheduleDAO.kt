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
    @Query("SELECT * FROM Schedule")
    fun getAll(): List<Schedule> //Coroutine 내에서 작동하기에 suspend가 붙는다.

    @Query("SELECT * FROM Schedule WHERE date = :d")
    fun getDateSchedule(d: String): List<Schedule>

    @Query("SELECT * FROM Schedule WHERE date = :d AND startTime BETWEEN :s AND :e")
    fun inspectionA(d: String, s: Int, e: Int): List<Schedule>

    @Query("SELECT * FROM Schedule WHERE date = :d AND endTime BETWEEN :s AND :e")
    fun inspectionB(d: String, s: Int, e: Int): List<Schedule>

    @Query("SELECT * FROM Schedule WHERE date = :d AND startTime <= :s AND endTime >= :e")
    fun inspectionC(d: String, s: Int, e: Int): List<Schedule>

    @Insert
    fun insert(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)
}