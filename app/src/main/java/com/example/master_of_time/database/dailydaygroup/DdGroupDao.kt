package com.example.master_of_time.database.dailydaygroup

import androidx.room.*

@Dao
interface DdGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ddGroup: DdGroup)

    @Update
    suspend fun update(ddGroup: DdGroup)

    @Delete
    suspend fun delete(ddGroup: DdGroup)
}