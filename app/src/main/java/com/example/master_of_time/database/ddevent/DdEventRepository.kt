package com.example.master_of_time.database.ddevent

import kotlinx.coroutines.flow.Flow


interface DdEventRepository {

    suspend fun insert(ddEvent: DdEvent)

    suspend fun update(ddEvent: DdEvent)

    suspend fun delete(ddEvent: DdEvent)

    suspend fun getDdEvent(id: Int): DdEvent

    suspend fun getAllDaylyDay(): List<DdEvent>

    fun getGroupName(id: Int): Flow<String>

    fun getDailyDayStream(id: Int): Flow<DdEvent>

    fun getAllDailyDayStream(): Flow<List<DdEvent>>

}