package com.example.master_of_time.database.dailydaygroup

import androidx.room.*
import com.example.master_of_time.database.dailyday.DailyDay

@Dao
interface DailyDayGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dailyDayGroup: DailyDayGroup)

    @Update
    suspend fun update(dailyDayGroup: DailyDayGroup)

    @Delete
    suspend fun delete(dailyDayGroup: DailyDayGroup)
}