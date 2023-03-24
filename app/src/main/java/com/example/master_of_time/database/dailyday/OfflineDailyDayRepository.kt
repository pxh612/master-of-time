package com.example.master_of_time.database.dailyday

import kotlinx.coroutines.flow.Flow

class OfflineDailyDayRepository(private var dailyDayDao: DailyDayDao) : DailyDayRepository {
    override suspend fun insert(dailyDay: DailyDay) = dailyDayDao.insert(dailyDay)

    override suspend fun update(dailyDay: DailyDay) = dailyDayDao.update(dailyDay)

    override suspend fun delete(dailyDay: DailyDay) = dailyDayDao.delete(dailyDay)

    override fun getDailyDayStream(id: Int): Flow<DailyDay> = dailyDayDao.getDailyDayFlow(id)

    override suspend fun getDailyDay(id: Int): DailyDay = dailyDayDao.getDailyDay(id)

    override fun getAllDailyDayStream(): Flow<List<DailyDay>> = dailyDayDao.getAllDailyDayFlow()

    override suspend fun getAllDaylyDay(): List<DailyDay> = dailyDayDao.getAllDailyDay()
}