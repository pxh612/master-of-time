package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.*
import com.example.master_of_time.database.dao.DailyDayDao
import timber.log.Timber


class DdEventViewModel(
    private val dailyDayDao: DailyDayDao

) : ViewModel(){


    fun getAllDdEvent() = dailyDayDao.getAllDdEvent()

    fun getAllDdGroup() = dailyDayDao.getAllDdGroup()

    fun getDdEventListByGroupId(groupId: Long?) = dailyDayDao.getDdEventListByGroupId(groupId)

    class Factory(
        private val dailyDayDao: DailyDayDao
    ) : ViewModelProvider.Factory {

        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(DdEventViewModel::class.java)){
                Timber.v("> create ViewModel")
                @Suppress("UNCHECKED_CAST")
                return DdEventViewModel(dailyDayDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

