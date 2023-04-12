package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.*
import com.example.master_of_time.database.dao.DailyDayDao
import com.example.master_of_time.database.table.DdEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class DdEventEditViewModel(
    private val dailyDayDao: DailyDayDao
) : ViewModel(){

    var ddEventCalculationTypeIndex: Int? = null

    fun getDdEvent(id: Long): LiveData<DdEvent> {
        return dailyDayDao.getDdEvent(id).asLiveData()
    }

    fun updateItem(ddEvent: DdEvent) {
        viewModelScope.launch(Dispatchers.IO){
            dailyDayDao.update(ddEvent)
        }
    }

    fun insertItem(ddEvent: DdEvent) {
        Timber.d("> insert new event")
        viewModelScope.launch(Dispatchers.IO){
            dailyDayDao.insert(ddEvent)
        }
    }

    fun deleteItem(ddEvent: DdEvent) {
        viewModelScope.launch(Dispatchers.IO){
            dailyDayDao.delete(ddEvent)
        }
    }

    fun getGroupName_byGroupId(id: Long): LiveData<String?> = dailyDayDao.getGroupName_byGroupId(id).asLiveData()

    fun getDdGroup(groupId: Long) = dailyDayDao.getDdGroup(groupId).asLiveData()
}

class DdEventEditViewModelFactory(
    private val dailyDayDao: DailyDayDao
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass
                .isAssignableFrom(DdEventEditViewModel::class.java)){
            Timber.v("> create ViewModel")
            @Suppress("UNCHECKED_CAST")
            return DdEventEditViewModel(dailyDayDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}





