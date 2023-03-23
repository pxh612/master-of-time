package com.example.pxh612_trivia_practice.database.daily_day

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pxh612_trivia_practice.database.DatabaseNames.DAILY_DAY_TABLE

@Database(entities = [DailyDay::class], version = 1, exportSchema = false)
abstract class DailyDayDatabase : RoomDatabase(){
    abstract fun dailyDayDao(): DailyDayDao
    companion object{
        @Volatile
        private var INSTANCE : DailyDayDatabase? = null
        fun getInstance(context : Context) : DailyDayDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DailyDayDatabase::class.java,
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