package com.example.humancorporation

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrateDatabase {
    // 버전별 변경내역 생성 : 버전 1에서 2로 바뀔 때 적용되는 사항
    val MIGRATE_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 변경 쿼리
            val alter = "ALTER table Schedule add column typeNum INTEGER NOT NULL default 3"
            // 쿼리 적용
            database.execSQL(alter)
        }
    }

}