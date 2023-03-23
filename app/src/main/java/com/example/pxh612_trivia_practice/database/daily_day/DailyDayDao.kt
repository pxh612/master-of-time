package com.example.pxh612_trivia_practice.database.daily_day

import androidx.room.*
import com.example.pxh612_trivia_practice.database.DatabaseNames.DAILY_DAY_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dailyDay: DailyDay)
    @Update
    suspend fun update(dailyDay: DailyDay)
    @Delete
    suspend fun delete(dailyDay: DailyDay)
    @Query("SELECT * FROM $DAILY_DAY_TABLE WHERE id = :id")
    fun getDailyDay(id: Int): Flow<DailyDay>
    @Query("SELECT * from $DAILY_DAY_TABLE ORDER BY id ASC")
    fun getAllDailyDay(): Flow<List<DailyDay>>
}