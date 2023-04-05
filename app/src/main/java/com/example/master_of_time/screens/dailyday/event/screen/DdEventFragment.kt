package com.example.master_of_time.screens.dailyday.event.screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.database.ddevent.DdEvent
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.ddevent.DdEventRepository
import com.example.master_of_time.database.ddevent.OfflineDdEventRepository
import com.example.master_of_time.databinding.DdEventFragmentBinding
import com.example.master_of_time.screens.dailyday.event.DdEventAdapter
import com.example.master_of_time.screens.dailyday.event.DdEventLayoutManager
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import com.example.master_of_time.screens.dailyday.event.DdEventViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class DdEventFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : DdEventFragmentBinding
    private lateinit var viewModel: DdEventViewModel
    private lateinit var ddEventRepository: DdEventRepository

    lateinit var ddEventLayoutManager: DdEventLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dd_event_fragment,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** init Custom Classes */
        ddEventRepository = OfflineDdEventRepository(AppDatabase.getInstance(requireContext()).ddEventDao())
        ddEventLayoutManager = DdEventLayoutManager(requireContext())


        /** init ViewModel */
        viewModel = ViewModelProvider(
            requireActivity(),
            DdEventViewModelFactory(ddEventRepository)
        )[DdEventViewModel::class.java]


        /** init Adapter for RecyclerView*/
        val ddEventAdapter = DdEventAdapter { DailyDay -> onItemClicked(DailyDay) }
        lifecycle.coroutineScope.launch {
            viewModel.getAllDailyDay().collect() {
                Timber.v("> collect FlowList for adapter: size = ${it.size}")
                ddEventAdapter.submitList(it)
            }
        }


        /** init View */
        binding.run {

            recylerView.run {
                layoutManager = ddEventLayoutManager.value
                adapter = ddEventAdapter
            }

            header.run {
                add.setOnClickListener(this@DdEventFragment)
                layout.setOnClickListener(this@DdEventFragment)
                buttonOne.setOnClickListener(this@DdEventFragment)
            }

        }


    }



    private fun onItemClicked(ddEvent: DdEvent) {
        Timber.i("> Item clicked: $ddEvent")
        navigateToEditScreen(ddEvent.id)
    }

    override fun onClick(view: View) {
        Timber.v("> reponsive click on DailyDayFragment ")
        when(view.id){
            R.id.add -> navigateToAddScreen()
            R.id.layout -> {
                ddEventLayoutManager.changeLayout()
                binding.recylerView.layoutManager = ddEventLayoutManager.value
            }
            R.id.buttonOne -> {
                navigateToGroupList()
            }

        }
    }

    private fun navigateToGroupList() {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdGroupFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToAddScreen() {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(true)
        requireView().findNavController().navigate(action)
    }

    private fun navigateToEditScreen(eventId: Int) {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(false, eventId)
        requireView().findNavController().navigate(action)
    }
}


// ========================================== IGNORE

// === RecyclerView
// https://developer.android.com/codelabs/basic-android-kotlin-training-recyclerview-scrollable-list?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-2-pathway-3%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-recyclerview-scrollable-list#3

// === ViewModelFactory
// https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-5-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-intro-room-flow#7
