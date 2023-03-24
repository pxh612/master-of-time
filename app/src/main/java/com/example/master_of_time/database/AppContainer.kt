package com.example.master_of_time.database

import android.content.Context
import com.example.master_of_time.database.dailyday.DailyDayDatabase
import com.example.master_of_time.database.dailyday.DailyDayRepository
import com.example.master_of_time.database.dailyday.OfflineDailyDayRepository


interface AppContainer {
    val dailyDayRepository: DailyDayRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val dailyDayRepository: DailyDayRepository by lazy {
        OfflineDailyDayRepository(DailyDayDatabase.getInstance(context).dailyDayDao())
    }
}