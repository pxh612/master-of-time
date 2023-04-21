package com.example.master_of_time.screens.dailyday.history

import androidx.lifecycle.*
import com.example.master_of_time.database.dao.DailyDayDao
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.table.DdEventHistory
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class DdEventHistoryEditViewModel(
    private val dailyDayDao: DailyDayDao
): ViewModel() {

    fun insertEventHistory(ddEventHistory: DdEventHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            dailyDayDao.insert(ddEventHistory)
        }
    }
    fun deleteDdEventHistory(ddEventHistory: DdEventHistory) {
        viewModelScope.launch(Dispatchers.IO){
            dailyDayDao.delete(ddEventHistory)
        }
    }
    fun updateEventHistory(ddEventHistory: DdEventHistory) {
        viewModelScope.launch(Dispatchers.IO){
            dailyDayDao.update(ddEventHistory)
        }
    }

    fun getDdEventHistory(ddEventHistoryId: Long) = dailyDayDao.getDdEventHistory(ddEventHistoryId).asLiveData()

    fun getDdEvent(id: Long): LiveData<DdEvent> = dailyDayDao.getDdEvent(id).asLiveData()



    class Factory(
        private val dailyDayDao: DailyDayDao
    ) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(DdEventHistoryEditViewModel::class.java)){
                Timber.v("> create ViewModel")
                @Suppress("UNCHECKED_CAST")
                return DdEventHistoryEditViewModel(dailyDayDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
