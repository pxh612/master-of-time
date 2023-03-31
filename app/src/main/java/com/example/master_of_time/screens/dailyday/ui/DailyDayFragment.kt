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
import com.example.master_of_time.database.dailyday.DailyDay
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.dailyday.DailyDayRepository
import com.example.master_of_time.database.dailyday.OfflineDailyDayRepository
import com.example.master_of_time.databinding.FragmentDailyDayBinding
import com.example.master_of_time.screens.dailyday.*
import com.example.master_of_time.screens.dailyday.ui.adapter.DailyDayAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class DailyDayFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentDailyDayBinding
    private lateinit var viewModel: DailyDayViewModel
    private lateinit var dailyDayRepository: DailyDayRepository

    lateinit var dailyDayLayoutManager: DailyDayLayoutManager
    var selectedTable: Int = 0


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


        /** init Custom Classes */
        dailyDayRepository = OfflineDailyDayRepository(AppDatabase.getInstance(requireContext()).dailyDayDao())
        dailyDayLayoutManager = DailyDayLayoutManager(requireContext())


        /** init ViewModel */
        viewModel = ViewModelProvider(
            requireActivity(),
            DailyDayViewModelFactory(dailyDayRepository)
        )[DailyDayViewModel::class.java]


        /** init Adapter for RecyclerView*/

        val dailyDayAdapter = DailyDayAdapter { DailyDay -> onAdapterClicked(DailyDay) }

        lifecycle.coroutineScope.launch {
            viewModel.getAllDailyDay().collect() {
                Timber.v("> collect FlowList for adapter: size = ${it.size}")
                dailyDayAdapter.submitList(it)
            }
        }



        /** init View */
        binding.run {
            /** init RecyclerView */
            recylerView.run {
                layoutManager = dailyDayLayoutManager.getLayout()

                adapter = dailyDayAdapter
            }

            /** init Button */
            header.run {
                add.setOnClickListener(this@DailyDayFragment)
                layout.setOnClickListener(this@DailyDayFragment)
                table.setOnClickListener(this@DailyDayFragment)
            }
        }
    }



    private fun onAdapterClicked(dailyDay: DailyDay) {
        Timber.i("Item clicked: id = ${dailyDay.id} && title = ${dailyDay.title}")
        navigateToEditScreen(dailyDay.id)
    }

    override fun onClick(view: View) {
        Timber.v("> reponsive click on DailyDayFragment ")
        when(view.id){
            R.id.add -> navigateToAddScreen()
            R.id.layout -> {
                dailyDayLayoutManager.changeLayout()
                binding.recylerView.layoutManager = dailyDayLayoutManager.getLayout()
            }
            R.id.table -> changeTable()
        }
    }

    private fun changeTable() {
        selectedTable = TABLE.cycleThrough(selectedTable)
        setTable(selectedTable)
    }

    private fun setTable(table: Int) {
        Timber.d("> do nothing")
        when(table){
            TABLE.TABLE_DAILY_DAY -> {

            }
            TABLE.TABLE_DAILY_DAY_GROUP -> {

            }
        }
    }


    private fun navigateToAddScreen() {
        val action = DailyDayFragmentDirections.actionDailyDayFragmentToDailyDayEditFragment(0, true)
        requireView().findNavController().navigate(action)
    }

    private fun navigateToEditScreen(dailyDayId: Int) {
        val action = DailyDayFragmentDirections.actionDailyDayFragmentToDailyDayEditFragment(dailyDayId, false)
        requireView().findNavController().navigate(action)
    }

}


// ========================================== IGNORE

// === RecyclerView
// https://developer.android.com/codelabs/basic-android-kotlin-training-recyclerview-scrollable-list?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-2-pathway-3%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-recyclerview-scrollable-list#3

// === ViewModelFactory
// https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-5-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-intro-room-flow#7
