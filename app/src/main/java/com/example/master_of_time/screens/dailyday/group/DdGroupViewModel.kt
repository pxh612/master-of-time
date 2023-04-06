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

    fun insertGroup(name: String) {
        val ddGroup = DdGroup(name = name)
        viewModelScope.launch(Dispatchers.IO) {
            dailyDayDao.insert(ddGroup)
        }
    }

    fun updateGroup(ddGroup: DdGroup) {
        viewModelScope.launch(Dispatchers.IO) {
            dailyDayDao.update(ddGroup)
        }
    }

    fun getAllDdGroup(): Flow<List<DdGroup>> = dailyDayDao.getAllDdGroup()

    fun getDdGroupName(groupId: Int): LiveData<String> = dailyDayDao.getGroupName(groupId).asLiveData()

    fun getDdEventCount_byGroupId(groupId: Int) = dailyDayDao.getDdEventCount_byGroupId(groupId).asLiveData()

    suspend fun getDdEventCount_byGroupId_NoFlow(groupId: Int): Int = dailyDayDao.getDdEventCount_byGroupId_NoFlow(groupId)



//    fun displayGroupName_DdGroupItem(item: DdGroup): String {
//
//        var resultString: String = ""
//        var count: Int = 0
//        viewModelScope.launch(Dispatchers.IO) {
//            count = getDdEventCount_byGroupId(item.id)
//            Timber.d("count = $count")
//            Timber.d("item = $item")
//            resultString = "${item.name} ($count)"
//            resultString
//        }
//
//
//
//        return resultString
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

