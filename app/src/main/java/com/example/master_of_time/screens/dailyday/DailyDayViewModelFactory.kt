package com.example.master_of_time.screens.dailyday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.master_of_time.database.dailyday.DailyDayRepository
import timber.log.Timber

class DailyDayViewModelFactory(
    private val dailyDayRepository: DailyDayRepository
) : ViewModelProvider.Factory {


    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(DailyDayViewModel::class.java)){
            Timber.v("> create ViewModel")
            @Suppress("UNCHECKED_CAST")
            return DailyDayViewModel(dailyDayRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
