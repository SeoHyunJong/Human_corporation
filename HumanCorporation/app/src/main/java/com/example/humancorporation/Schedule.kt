package com.example.humancorporation

import androidx.room.*

/*
DB 에는 어떤 정보가 담겨져야 하나?
0. id
-> Primarykey: auto generated 할수 있게
1. 날짜 date
2. 시작 시간 startTime
3. 종료 시간 endTime
4. 내용 contents
5. 생산적/비생산적/중립 typeNum (1~3)
 */
@Entity(tableName = "Schedule")
data class Schedule(@PrimaryKey(autoGenerate = true) val id: Int,
                    @ColumnInfo(name = "date") val date: Long,
                    @ColumnInfo(name = "startTime") val startTime: Int, 
                    @ColumnInfo(name = "endTime") val endTime: Int,
                    @ColumnInfo(name = "contents") val contents: String,
                    @ColumnInfo(name = "typeNum") val typeNum: Int)