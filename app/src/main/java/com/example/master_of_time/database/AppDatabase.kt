package com.example.master_of_time.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import com.example.master_of_time.database.dailyday.DdEvent
import com.example.master_of_time.database.dailyday.DdEventDao
import com.example.master_of_time.database.dailydaygroup.DdGroup
import com.example.master_of_time.database.dailydaygroup.DdGroupDao

//@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
// Fatal: need to update the version number
// Fatal:  FOREIGN KEY constraint failed

@Database(entities = [DdEvent::class, DdGroup::class], version = 7, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun ddEventDao(): DdEventDao
    abstract fun ddGroupDao(): DdGroupDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context : Context) : AppDatabase {
            return INSTANCE ?: synchronized(this){

                /** return DAILY_DAY_TABLE: need to also return Group */
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}