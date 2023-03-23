package com.example.pxh612_trivia_practice.database.daily_day

import kotlinx.coroutines.flow.Flow


interface DailyDayRepository {
    suspend fun insert(dailyDay: DailyDay)
    suspend fun update(dailyDay: DailyDay)
    suspend fun delete(dailyDay: DailyDay)
    fun getDailyDayStream(id: Int): Flow<DailyDay>
    fun getAllDailyDayStream(): Flow<List<DailyDay>>
}