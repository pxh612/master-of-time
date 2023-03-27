package com.example.master_of_time.database.dailyday

import kotlinx.coroutines.flow.Flow


interface DailyDayRepository {

    suspend fun insert(dailyDay: DailyDay)

    suspend fun update(dailyDay: DailyDay)

    suspend fun delete(dailyDay: DailyDay)

    suspend fun getDailyDay(id: Int): DailyDay

    suspend fun getAllDaylyDay(): List<DailyDay>

    fun getDailyDayStream(id: Int): Flow<DailyDay>

    fun getAllDailyDayStream(): Flow<List<DailyDay>>
}