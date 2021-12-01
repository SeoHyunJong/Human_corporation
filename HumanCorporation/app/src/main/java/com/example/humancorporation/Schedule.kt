package com.example.humancorporation

import androidx.room.*

/*
DB 에는 어떤 정보가 담겨져야 하나?
1. 날짜 date
2. 시작 시간 startTime
3. 종료 시간 endTime
4. 내용 contents
5. 스코어 score -> 다른 테이블에 있는게 어울릴듯
-> 하루는 1440분, 1분당 0.02%
6. 현재 인생가격 currentStock -> 다른 테이블에 있는게 어울릴듯
7. id
-> Primarykey: auto generated 할수 있게
 */
@Entity(tableName = "Schedule")
data class Schedule(@PrimaryKey(autoGenerate = true) val id: Int,
                    @ColumnInfo(name = "date") val date: String, 
                    @ColumnInfo(name = "startTime") val startTime: Int, 
                    @ColumnInfo(name = "endTime") val endTime: Int,
                    @ColumnInfo(name = "contents") val contents: String)