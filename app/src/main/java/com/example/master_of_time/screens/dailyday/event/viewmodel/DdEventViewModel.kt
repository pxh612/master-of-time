package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.*
import com.example.master_of_time.database.dao.DailyDayDao
import timber.log.Timber


class DdEventViewModel(
    private val dailyDayDao: DailyDayDao

) : ViewModel(){


    fun getAllDdEvent() = dailyDayDao.getAllDdEvent()

    fun getAllDdGroup() = dailyDayDao.getAllDdGroup()

    fun getDdEventListByGroupId(groupId: Long?) = dailyDayDao.getDdEventListByGroupId(groupId)

}

class DdEventViewModelFactory(
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






// ========================================== IGNORE


// === remember { mutableStateOf("") }
// https://stefma.medium.com/jetpack-compose-remember-mutablestateof-derivedstateof-and-remembersaveable-explained-270dbaa61b8
//
// belong to Jetpack Compose
// foundation behind compose: UI widget updated -> compose call composable function again -> "recomposition"


// === Couroutines
// https://developer.android.com/kotlin/coroutines
// https://developer.android.com/topic/libraries/architecture/coroutines
//
// === Sử dụng ViewModelScope để giảm Boilerplate code với Coroutines
// https://viblo.asia/p/su-dung-viewmodelscope-de-giam-boilerplate-code-voi-coroutines-aWj53e9eZ6m
// https://amitshekhar.me/blog/kotlin-coroutines
// Scopes in Kotlin Coroutines are very useful because we need to cancel the background task as soon as the activity is destroyed.

// === Fixed bug ===
// https://stackoverflow.com/questions/70567066/cannot-access-database-on-the-main-thread-since-it-may-potentially-lock-the-ui

// === Pass Dao, not repository?
// https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow#5
