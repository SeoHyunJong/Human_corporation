package com.example.humancorporation

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Schedule::class, CSStock::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDAO
    abstract fun stockDAO(): CSStockDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "humanCorp.db"
                    ).addMigrations(MigrateDatabase.MIGRATE_1_2).build()
                }
            }
            return INSTANCE
        }
    }
}