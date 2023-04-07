package com.example.master_of_time.screens.dailyday.group.viewmodel

import androidx.lifecycle.*
import com.example.master_of_time.database.dao.DailyDayDao
import com.example.master_of_time.database.table.DdGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class DdGroupViewModel(
    private val dailyDayDao: DailyDayDao
    ): ViewModel() {


    /** LiveData: selectedGroupId */
    var selectedGroupId: Long = -1
        set(value){
            selectedGroupId_MutableLiveData.value = value
            field = value
        }
    private val selectedGroupId_MutableLiveData = MutableLiveData<Long>()
    val selectedGroupId_LiveData: LiveData<Long>
        get() = selectedGroupId_MutableLiveData

    /** LiveData: newGroupId */
    var newGroupId: Long = -1
        set(value){
            newGroupId_MLD.value = value
            field = value
        }
    private val newGroupId_MLD = MutableLiveData<Long>()
    val newGroupId_LiveData: LiveData<Long>
        get() = newGroupId_MLD


    fun insertGroup(ddGroup: DdGroup) {
        viewModelScope.launch(Dispatchers.IO) {
            newGroupId = dailyDayDao.insert(ddGroup)
        }
    }

    fun updateGroup(ddGroup: DdGroup) {
        viewModelScope.launch(Dispatchers.IO) { dailyDayDao.update(ddGroup) }
    }

    fun getAllDdGroup(): Flow<List<DdGroup>> = dailyDayDao.getAllDdGroup()

    fun getDdGroupName(groupId: Long): LiveData<String> = dailyDayDao.getGroupName_byGroupId(groupId).asLiveData()

    fun getDdEventCount_byGroupId(groupId: Long) = dailyDayDao.getDdEventCount_byGroupId(groupId).asLiveData()


    class Factory(
        private val dailyDayDao: DailyDayDao
    ) : ViewModelProvider.Factory {

        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(DdGroupViewModel::class.java)){
                Timber.v("> create ViewModel")
                @Suppress("UNCHECKED_CAST")
                return DdGroupViewModel(dailyDayDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


