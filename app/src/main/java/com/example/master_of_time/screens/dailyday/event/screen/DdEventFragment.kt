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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.master_of_time.R
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdEventFragmentBinding
import com.example.master_of_time.screens.dailyday.event.DdEventListAdapter
import com.example.master_of_time.screens.dailyday.event.DdEventLayoutManager
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import com.example.master_of_time.screens.dailyday.event.DdEventViewModelFactory
import com.example.master_of_time.screens.dailyday.group.DisplayEventsDdGroupAdapter
import com.example.master_of_time.screens.dailyday.group.DisplayEventsDdGroupAdapterListener
import kotlinx.coroutines.launch
import timber.log.Timber

class DdEventFragment : Fragment(), View.OnClickListener, DisplayEventsDdGroupAdapterListener {

    private lateinit var binding : DdEventFragmentBinding
    private lateinit var viewModel: DdEventViewModel

    /** views */
    lateinit var ddEventLayoutManager: DdEventLayoutManager
    val ddEventListAdapter = DdEventListAdapter { onItemClicked(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.dd_event_fragment, container, false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        ddEventLayoutManager = DdEventLayoutManager(requireContext())


        viewModel = ViewModelProvider(
            requireActivity(),
            DdEventViewModelFactory(dailyDayDao)
        )[DdEventViewModel::class.java]



        val displayEventsDdGroupAdapter = DisplayEventsDdGroupAdapter(this)
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect() {
                displayEventsDdGroupAdapter.submitList(it)
            }
        }
        onUpdate_PickedDdGroupId_DdEventListAdapter(groupId = null)

        binding.run {

            header.run {
                add.setOnClickListener(this@DdEventFragment)
                layout.setOnClickListener(this@DdEventFragment)
                buttonOne.setOnClickListener(this@DdEventFragment)
            }

            groupRecyclerView.run {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = displayEventsDdGroupAdapter
            }

            eventRecyclerView.run {
                layoutManager = ddEventLayoutManager.value
                adapter = ddEventListAdapter
            }
        }
    }

    override fun onClick(view: View) {
        Timber.v("> reponsive click on DailyDayFragment ")
        when(view.id){
            R.id.add -> navigateToAddScreen()
            R.id.layout -> {
                ddEventLayoutManager.changeLayout()
                binding.eventRecyclerView.layoutManager = ddEventLayoutManager.value
            }
            R.id.buttonOne -> {
                navigateToGroupList()
            }

        }
    }

    override fun onUpdate_PickedDdGroupId_DdEventListAdapter(groupId: Int?) {
        when(groupId){
            null -> {
                lifecycle.coroutineScope.launch {
                    viewModel.getAllDdEvent().collect() {
                        ddEventListAdapter.submitList(it)
                    }
                }
            }
            else -> {
                lifecycle.coroutineScope.launch {
                    viewModel.getDdEventListByGroupId(groupId).collect() {
                        ddEventListAdapter.submitList(it)
                    }
                }
            }
        }
    }




    private fun updateEventListAdapter(ddGroup: DdGroup) {

    }

    private fun onItemClicked(ddEvent: DdEvent) {
        Timber.i("> Item clicked: $ddEvent")
        navigateToEditScreen(ddEvent.id)
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
