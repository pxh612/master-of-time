package com.example.master_of_time.database.ddevent

import kotlinx.coroutines.flow.Flow

class OfflineDdEventRepository(
    private var ddEventDao: DdEventDao
    ) : DdEventRepository {

    override suspend fun insert(ddEvent: DdEvent) = ddEventDao.insert(ddEvent)

    override suspend fun update(ddEvent: DdEvent) = ddEventDao.update(ddEvent)

    override suspend fun delete(ddEvent: DdEvent) = ddEventDao.delete(ddEvent)

    override fun getDailyDayStream(id: Int): Flow<DdEvent> = ddEventDao.getDailyDayFlow(id)

    override suspend fun getDdEvent(id: Int): DdEvent = ddEventDao.getDailyDay(id)

    override fun getAllDailyDayStream(): Flow<List<DdEvent>> = ddEventDao.getAllDailyDayFlow()

    override suspend fun getAllDaylyDay(): List<DdEvent> = ddEventDao.getAllDailyDay()

    override fun getGroupName(id: Int): Flow<String> = ddEventDao.getGroupName(id)


}