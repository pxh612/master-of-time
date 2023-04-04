package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.master_of_time.database.dailyday.DdEventRepository
import timber.log.Timber

class DailyDayViewModelFactory(
    private val ddEventRepository: DdEventRepository
) : ViewModelProvider.Factory {


    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(DdEventViewModel::class.java)){
            Timber.v("> create ViewModel")
            @Suppress("UNCHECKED_CAST")
            return DdEventViewModel(ddEventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
