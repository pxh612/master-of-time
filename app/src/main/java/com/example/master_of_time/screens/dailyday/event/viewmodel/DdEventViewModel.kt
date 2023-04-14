package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.*
import com.example.master_of_time.database.dao.DailyDayDao
import com.example.master_of_time.module.animation.MyAnimator
import timber.log.Timber


class DdEventViewModel(
    private val dailyDayDao: DailyDayDao

) : ViewModel(){


    /** Data */
    var lastSelectedAdapterPosition: Int = -1
    var selectedGroupId: Long = -1L
    val addMyAnimator = MyAnimator()


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

