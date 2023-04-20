package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.*
import com.example.master_of_time.database.dao.DailyDayDao
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.module.MyAnimator
import com.example.master_of_time.module.dailyday.DdEventListSorter
import timber.log.Timber


class DdEventViewModel(
    private val dailyDayDao: DailyDayDao

) : ViewModel(){


    /** Data */
    var ddEventListSorter = DdEventListSorter()

    var lastSelectedAdapterPosition: Int = -1
    var selectedGroupId: Long = -1L
    val addMyAnimator = MyAnimator()

//    fun sortSomething() { return ddEventListSorter.sortSomething()}

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

