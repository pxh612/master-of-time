package com.example.master_of_time.screens.dailyday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.master_of_time.database.dailyday.DailyDayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class DailyDayViewModel(
    dailyDayRepository: DailyDayRepository
) : ViewModel(){

    /** LiveData */
    private val _simpleDisplayMessage = MutableLiveData<String>()
    val simpleDisplayMessage: LiveData<String>
        get() = _simpleDisplayMessage


    init{
        _simpleDisplayMessage.value = "from DailyDayViewModel with love"

        Timber.v("...init...")


        /** Coroutine */
        //      reference: app Inventory / class ItemEditViewModel
        //          pass to a data class: ItemUIState
        viewModelScope.launch (Dispatchers.IO) {
            Timber.v("...enter coroutine ...")

            /** Attempt: read from Flow */
//            var repos = dailyDayRepository.getAllDailyDayStream()
//            var reposLogOutput: String = "["
//            Timber.d("dailyDayList = $reposLogOutput")
//
//            repos.collect{ list ->
//                Timber.d("...inside flow collect...")
//                Timber.i("list size = ${list.size}")
//                list.forEach {
//                    reposLogOutput += "$it, "
//                }
//            }
//            reposLogOutput += "]"
//            Timber.i("dailyDayList = $reposLogOutput")

            /** Attempt: read list from suspend function */
            Timber.e("FATAL: java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.")
//            var list = dailyDayRepository.getAllDaylyDay()  // <- Fatal
            var item = dailyDayRepository.getDailyDay(69)



            /** Attempt: insert Item to Repos - success! */
            Timber.v("...testing repos...")
//
//            val sampleDailyDay = DailyDay(686, "SampleTitle__", 16796247918)
//            dailyDayRepository.insert(sampleDailyDay)
//
//            var repos = dailyDayRepository.getAllDailyDayStream()
//            repos.collect{list ->
//                Timber.i("list size = ${list.size}")
//                list.forEach {
//                    Timber.d("${it.id} & ${it.date} & ${it.title} \n")
//                }
//            }
//

            /** Attempt: read from Repos */


        }
    }

}


// Class-declaration attempt: Dagger
//
//@HiltViewModel
//class DailyDayViewModel @Inject constructor(
//    savedStateHandle: SavedStateHandle,
//    repository: DailyDayRepository
//) : ViewModel() {
//
//    init{
//        Timber.v("...init...")
//    }
//}


// === Knowledge: ===
// https://stefma.medium.com/jetpack-compose-remember-mutablestateof-derivedstateof-and-remembersaveable-explained-270dbaa61b8
// val stringState = remember { mutableStateOf("") }
//
// belong to Jetpack Compose
// foundation behind compose: UI widget updated -> compose call composable function again -> "recomposition"


// === Knowledge === Couroutines
// https://developer.android.com/kotlin/coroutines
// https://developer.android.com/topic/libraries/architecture/coroutines
//
//  Sử dụng ViewModelScope để giảm Boilerplate code với Coroutines
// https://viblo.asia/p/su-dung-viewmodelscope-de-giam-boilerplate-code-voi-coroutines-aWj53e9eZ6m
// https://amitshekhar.me/blog/kotlin-coroutines
// Scopes in Kotlin Coroutines are very useful because we need to cancel the background task as soon as the activity is destroyed.