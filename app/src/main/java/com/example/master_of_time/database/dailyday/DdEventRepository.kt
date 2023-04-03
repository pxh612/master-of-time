package com.example.master_of_time.database.dailyday

import kotlinx.coroutines.flow.Flow


interface DdEventRepository {

    suspend fun insert(ddEvent: DdEvent)

    suspend fun update(ddEvent: DdEvent)

    suspend fun delete(ddEvent: DdEvent)

    suspend fun getDailyDay(id: Int): DdEvent

    suspend fun getAllDaylyDay(): List<DdEvent>

    fun getDailyDayStream(id: Int): Flow<DdEvent>

    fun getAllDailyDayStream(): Flow<List<DdEvent>>
}