package com.example.master_of_time.screens.dailyday.event

import androidx.lifecycle.*
import com.example.master_of_time.database.dailyday.DdEvent
import com.example.master_of_time.database.dailyday.DdEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DdEventViewModel(
    private val ddEventRepository: DdEventRepository
) : ViewModel(){

    // Store layout preference in ViewModel? In the same class or not?
    private val _selectedLayout = MutableLiveData<Int>()
    val selectedLayout: LiveData<Int>
        get() = _selectedLayout

    fun retrieveDailyDay(id: Int): LiveData<DdEvent> {
        return ddEventRepository.getDailyDayStream(id).asLiveData()
    }
    fun updateDailyDay(ddEvent: DdEvent){
        viewModelScope.launch(Dispatchers.IO){
            ddEventRepository.update(ddEvent)
        }
    }
    fun deleteDailyDay(ddEvent: DdEvent){
        viewModelScope.launch(Dispatchers.IO){
            ddEventRepository.delete(ddEvent)
        }
    }
    fun insertDailyDay(ddEvent: DdEvent) {
        viewModelScope.launch(Dispatchers.IO){
            ddEventRepository.insert(ddEvent)
        }
    }
    fun getAllDailyDay() = ddEventRepository.getAllDailyDayStream()

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
