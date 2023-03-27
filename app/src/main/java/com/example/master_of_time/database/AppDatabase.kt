package com.example.master_of_time.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.master_of_time.database.dailyday.DailyDay
import com.example.master_of_time.database.dailyday.DailyDayDao

/** GOOGLE CODE-LABS:
      https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-5-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-intro-room-flow#6

Now that you've defined the models, DAO, and a view model for fragments to access the DAO,
you still need to tell Room what to do with all of these classes.
That's where the AppDatabase class comes in. An Android app using Room,
such as yours, subclasses the RoomDatabase class and has a few key responsibilities.
In your app, the AppDatabase needs to

1. Specify which entities are defined in the database.
2. Provide access to a single instance of each DAO class.
3. Perform any additional setup, such as pre-populating the database.

 */

@Database(entities = [DailyDay::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dailyDayDao(): DailyDayDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/bus_schedule.db") // Warning: bus_schedule do not exist
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}