package com.example.master_of_time.database.dao

import androidx.room.*
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.table.DdGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDayDao {

    /** DdEvent **/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddEvent: DdEvent)

    @Update
    suspend fun update(ddEvent: DdEvent)

    @Delete
    suspend fun delete(ddEvent: DdEvent)

    @Query("SELECT * FROM DdEvent WHERE id = :id")
    fun getDdEvent(id: Int): Flow<DdEvent>

    @Query("SELECT * FROM DdEvent ORDER BY id ASC")
    fun getAllDdEvent(): Flow<List<DdEvent>>

    /** DdGroup **/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddGroup: DdGroup)

    @Update
    suspend fun update(ddGroup: DdGroup)

    @Delete
    suspend fun delete(ddGroup: DdGroup)

    @Query("SELECT * FROM DdGroup")
    fun getAllDdGroup(): Flow<List<DdGroup>>

    @Query(" SELECT name FROM DdGroup WHERE id = :groupId ")
    fun getGroupName(groupId: Int): Flow<String>


}