package com.example.master_of_time.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import com.example.master_of_time.database.dailyday.DailyDay
import com.example.master_of_time.database.dailyday.DailyDayDao
import com.example.master_of_time.database.dailydaygroup.DailyDayGroup

//@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
// Fatal: need to update the version number
// Fatal:  FOREIGN KEY constraint failed

@Database(entities = [DailyDay::class, DailyDayGroup::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun dailyDayDao(): DailyDayDao
    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context : Context) : AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DAILY_DAY_TABLE
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}