package com.example.master_of_time.screens.dailyday.group

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
/*

    val selectedGroupId: LiveData<Int>
        get() = _selectedGroupId
    private val _selectedGroupId = MutableLiveData<Int>()
*/

    var selectedGroupId = -1

    fun insertGroup(name: String) {
        val ddGroup = DdGroup(name = name)
        viewModelScope.launch(Dispatchers.IO) {
            dailyDayDao.insert(ddGroup)
        }
    }

    fun updateGroup(ddGroup: DdGroup) {
        Timber.d("update $ddGroup")
        viewModelScope.launch(Dispatchers.IO) {
            dailyDayDao.update(ddGroup)
        }
    }

    fun getAllDdGroup(): Flow<List<DdGroup>> = dailyDayDao.getAllDdGroup()

    fun getDdGroupName(groupId: Int): LiveData<String> = dailyDayDao.getGroupName_byGroupId(groupId).asLiveData()

    fun getDdEventCount_byGroupId(groupId: Int) = dailyDayDao.getDdEventCount_byGroupId(groupId).asLiveData()

//    fun setSelectedGroupId(groupId: Int){
//        selectedGroupId = groupId
//    }
}

class DdGroupViewModelFactory(
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

