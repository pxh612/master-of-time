package com.example.master_of_time.database.dailyday

import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface DdEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddEvent: DdEvent)

    @Update
    suspend fun update(ddEvent: DdEvent)

    @Delete
    suspend fun delete(ddEvent: DdEvent)

    @Query("SELECT * FROM $DAILY_DAY_TABLE WHERE id = :id")
    fun getDailyDay(id: Int): DdEvent

    @Query("SELECT * from $DAILY_DAY_TABLE ORDER BY id ASC")
    fun getAllDailyDay(): List<DdEvent>

    @Query("SELECT * FROM $DAILY_DAY_TABLE WHERE id = :id")
    fun getDailyDayFlow(id: Int): Flow<DdEvent>

    @Query("SELECT * from $DAILY_DAY_TABLE ORDER BY id ASC")
    fun getAllDailyDayFlow(): Flow<List<DdEvent>>

}