package com.example.master_of_time.screens.dailyday.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.master_of_time.R
import com.example.master_of_time.database.dailyday.DailyDayDatabase
import com.example.master_of_time.database.dailyday.DailyDayRepository
import com.example.master_of_time.database.dailyday.OfflineDailyDayRepository
import com.example.master_of_time.databinding.FragmentDailyDayBinding
import com.example.master_of_time.screens.dailyday.DailyDayViewModel
import com.example.master_of_time.screens.dailyday.DailyDayViewModelFactory
import com.example.master_of_time.screens.dailyday.ui.recyclerview.DailyDayAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class DailyDayFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = DailyDayFragment()
    }

    private lateinit var binding : FragmentDailyDayBinding
    private lateinit var viewModel: DailyDayViewModel
    private lateinit var dailyDayRepository: DailyDayRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_daily_day,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** init Repository */
        dailyDayRepository = OfflineDailyDayRepository(DailyDayDatabase.getInstance(requireContext()).dailyDayDao())

        /** init ViewModel */
        viewModel = ViewModelProvider(this, DailyDayViewModelFactory(dailyDayRepository))[DailyDayViewModel::class.java]

        /** init RecyclerView's Adapter: update with Flow<List> */
        val dailyDayAdapter = DailyDayAdapter()
        lifecycle.coroutineScope.launch {
            viewModel.getAllDailyDay().collect(){
                Timber.v("> collect viewModel's Flow<list> and pass to Adapter...")
                Timber.i("List.size = ${it.size}")
                dailyDayAdapter.submitList(it)
            }
        }

        /** init View */
        binding.run{
            /** init RecyclerView */
            recylerView.run {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = dailyDayAdapter
            }

            /** init Button */
            add.setOnClickListener(this@DailyDayFragment)

            /** start View */
            message.text = "from DailyDayFragment"
        }
    }

    override fun onClick(view: View) {
        Timber.v("> reponsive! ")
        when(view.id){
            R.id.add -> {
                navigateToEditScreen()
            }
        }
    }

    private fun navigateToEditScreen() {
        Timber.w("TODO")
//        requireView().findNavController().navigate(R.id.)
    }


}


// ========================================== NOTE

// === RecyclerView
// https://developer.android.com/codelabs/basic-android-kotlin-training-recyclerview-scrollable-list?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-2-pathway-3%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-recyclerview-scrollable-list#3
// item (data) + adapter (take data) + viewholders (pool of views) + recyclerview (view on screen)

// === ViewModelFactory
// https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-5-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-intro-room-flow#7
