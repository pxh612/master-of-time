package com.example.master_of_time.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.master_of_time.database.ddevent.DdEvent
import com.example.master_of_time.database.ddevent.DdEventDao
import com.example.master_of_time.database.ddgroup.DdGroup
import com.example.master_of_time.database.ddgroup.DdGroupDao

//@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
// Fatal: need to update the version number
// Fatal:  FOREIGN KEY constraint failed

@Database(entities = [DdEvent::class, DdGroup::class], version = 8, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun ddEventDao(): DdEventDao
    abstract fun ddGroupDao(): DdGroupDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context : Context) : AppDatabase {
            return INSTANCE ?: synchronized(this){

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