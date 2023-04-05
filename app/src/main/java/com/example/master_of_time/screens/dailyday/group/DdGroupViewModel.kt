package com.example.master_of_time.screens.dailyday.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.master_of_time.database.ddgroup.DdGroup
import com.example.master_of_time.database.ddgroup.DdGroupDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class DdGroupViewModel(
    private val ddGroupDao: DdGroupDao
    ): ViewModel() {

    fun insertGroup(name: String) {
        val ddGroup = DdGroup(name = name)
        viewModelScope.launch(Dispatchers.IO){
            ddGroupDao.insert(ddGroup)
        }
    }

    fun getAllDdGroup(): Flow<List<DdGroup>> = ddGroupDao.getAllDdGroupFlow()

}
class DdGroupViewModelFactory(
    private val dataSource: DdGroupDao
) : ViewModelProvider.Factory {

        override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(DdGroupViewModel::class.java)){
            Timber.v("> create ViewModel")
            @Suppress("UNCHECKED_CAST")
            return DdGroupViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

