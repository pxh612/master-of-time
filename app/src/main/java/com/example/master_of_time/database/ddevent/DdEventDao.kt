package com.example.master_of_time.database.ddevent

import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DD_EVENT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface DdEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddEvent: DdEvent)

    @Update
    suspend fun update(ddEvent: DdEvent)

    @Delete
    suspend fun delete(ddEvent: DdEvent)

    @Query("SELECT * FROM $DD_EVENT_TABLE WHERE id = :id")
    fun getDailyDay(id: Int): DdEvent

    @Query("SELECT * from $DD_EVENT_TABLE ORDER BY id ASC")
    fun getAllDailyDay(): List<DdEvent>

    @Query("SELECT * FROM $DD_EVENT_TABLE WHERE id = :id")
    fun getDailyDayFlow(id: Int): Flow<DdEvent>

    @Query("SELECT * from $DD_EVENT_TABLE ORDER BY id ASC")
    fun getAllDailyDayFlow(): Flow<List<DdEvent>>

    @Query(
        """
            SELECT g.name
            
            FROM $DD_EVENT_TABLE AS e
            LEFT JOIN DdGroup AS g
                ON e.groupId = g.id
            
            WHERE e.id = :id 
        """
    )
    fun getGroupName(id: Int): Flow<String>



}