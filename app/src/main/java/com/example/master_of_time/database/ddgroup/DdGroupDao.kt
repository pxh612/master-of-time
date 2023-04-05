package com.example.master_of_time.database.ddgroup

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DdGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddGroup: DdGroup)

    @Update
    suspend fun update(ddGroup: DdGroup)

    @Delete
    suspend fun delete(ddGroup: DdGroup)

    @Query("SELECT * from DdGroup")
    fun getAllDdGroupFlow(): Flow<List<DdGroup>>
}