package com.example.master_of_time.database.dailyday

import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
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
    fun getDailyDay(id: Int): DailyDay

    @Query("SELECT * from $DAILY_DAY_TABLE ORDER BY id ASC")
    fun getAllDailyDay(): List<DailyDay>

    @Query("SELECT * FROM $DAILY_DAY_TABLE WHERE id = :id")
    fun getDailyDayFlow(id: Int): Flow<DailyDay>

    @Query("SELECT * from $DAILY_DAY_TABLE ORDER BY id ASC")
    fun getAllDailyDayFlow(): Flow<List<DailyDay>>

}