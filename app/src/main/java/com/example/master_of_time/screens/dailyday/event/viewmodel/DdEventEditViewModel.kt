package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.*
import com.example.master_of_time.database.ddevent.DdEvent
import com.example.master_of_time.database.ddevent.DdEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class DdEventEditViewModel(
    private val ddEventRepository: DdEventRepository
) : ViewModel(){

    fun retrieveDailyDay(id: Int): LiveData<DdEvent> {
        return ddEventRepository.getDailyDayStream(id).asLiveData()
    }

    fun updateItem(ddEvent: DdEvent) {
        viewModelScope.launch(Dispatchers.IO){
            ddEventRepository.update(ddEvent)
        }
    }

    fun insertItem(ddEvent: DdEvent) {
        Timber.d("> insert new event")
        viewModelScope.launch(Dispatchers.IO){
            ddEventRepository.insert(ddEvent)
        }
    }

    fun deleteItem(ddEvent: DdEvent) {
        viewModelScope.launch(Dispatchers.IO){
            ddEventRepository.delete(ddEvent)
        }
    }

    fun getGroupName(id: Int?): LiveData<String?>? {
        return id?.let {
            ddEventRepository.getGroupName(id).asLiveData()
        }
    }



}

class DdEventEditViewModelFactory(
    private val ddEventRepository: DdEventRepository
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass
                .isAssignableFrom(DdEventEditViewModel::class.java)){
            Timber.v("> create ViewModel")
            @Suppress("UNCHECKED_CAST")
            return DdEventEditViewModel(ddEventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}





