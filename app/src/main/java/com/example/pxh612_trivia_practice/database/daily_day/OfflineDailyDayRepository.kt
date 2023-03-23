package com.example.pxh612_trivia_practice.database.daily_day

import kotlinx.coroutines.flow.Flow

class OfflineDailyDayRepository(private var dailyDayDao: DailyDayDao) : DailyDayRepository {
    override suspend fun insert(dailyDay: DailyDay) = dailyDayDao.insert(dailyDay)

    override suspend fun update(dailyDay: DailyDay) = dailyDayDao.update(dailyDay)

    override suspend fun delete(dailyDay: DailyDay) = dailyDayDao.delete(dailyDay)

    override fun getDailyDayStream(id: Int): Flow<DailyDay> = dailyDayDao.getDailyDay(id)

    override fun getAllDailyDayStream(): Flow<List<DailyDay>> = dailyDayDao.getAllDailyDay()
}