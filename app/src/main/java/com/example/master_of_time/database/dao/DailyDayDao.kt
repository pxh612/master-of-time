package com.example.master_of_time.database.dao

import androidx.room.*
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.table.DdEventHistory
import com.example.master_of_time.database.table.DdGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDayDao {

    /** DdEvent **/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddEvent: DdEvent) : Long

    @Update
    suspend fun update(ddEvent: DdEvent)

    @Delete
    suspend fun delete(ddEvent: DdEvent)

    @Query("SELECT * FROM DdEvent WHERE id = :id")
    fun getDdEvent(id: Long): Flow<DdEvent>

    @Query("SELECT * FROM DdEvent ORDER BY id ASC")
    fun getAllDdEvent(): Flow<List<DdEvent>>

    @Query("""
            SELECT * FROM DdEvent
            WHERE groupId = :groupId
        """)
    fun getDdEventListByGroupId(groupId: Long?): Flow<List<DdEvent>>

    @Query("""
            SELECT COUNT(*) FROM DdEvent
            WHERE groupId = :groupId
        """)
    fun getDdEventCount_byGroupId(groupId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM DdEvent WHERE groupId = :groupId")
    suspend fun getDdEventCount_byGroupId_NoFlow(groupId: Int): Int

    @Query("SELECT COUNT(*) FROM DdEvent")
    fun getDdEventTotalCount(): Flow<Int>


    /** DdGroup **/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddGroup: DdGroup) : Long

    @Update
    suspend fun update(ddGroup: DdGroup)

    @Delete
    suspend fun delete(ddGroup: DdGroup)

    @Query(" SELECT * FROM DdGroup WHERE id = :id ")
    fun getDdGroup(id: Long): Flow<DdGroup>

    @Query("SELECT * FROM DdGroup ORDER BY id ASC")
    fun getAllDdGroup_byPrimaryKeyId(): Flow<List<DdGroup>>

    @Query("SELECT * FROM DdGroup ORDER BY id ASC")
    fun getAllDdGroup(): Flow<List<DdGroup>>

    @Query(" SELECT name FROM DdGroup WHERE id = :groupId ")
    fun getGroupName_byGroupId(groupId: Long): Flow<String>



    /** DdEventHistory */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddEventHistory: DdEventHistory) : Long

    @Update
    suspend fun update(ddEventHistory: DdEventHistory)

    @Delete
    suspend fun delete(ddEventHistory: DdEventHistory)


    @Query(" SELECT * FROM DdEventHistory WHERE id = :id ")
    fun getDdEventHistory(id: Long): Flow<DdEventHistory>

    @Query(" SELECT * FROM DdEventHistory WHERE eventId = :eventId ORDER BY date")
    fun getDdEventHistoryOfEventId(eventId: Long): Flow<List<DdEventHistory>>


}