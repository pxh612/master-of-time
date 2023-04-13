package com.example.master_of_time.screens.dailyday.event.screen

import android.annotation.SuppressLint
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
import com.example.master_of_time.databinding.DdEventFragmentBinding
import com.example.master_of_time.module.dailyday.DdEventListSorter
import com.example.master_of_time.screens.dailyday.event.DdEventLayoutManager
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventRecyclerViewAdapter
import com.example.master_of_time.screens.dailyday.group.DisplayEventsDdGroupAdapter
import kotlinx.coroutines.launch

class DdEventFragment : Fragment(), View.OnClickListener, DisplayEventsDdGroupAdapter.Listener {

    /** Architecture */
    private lateinit var binding : DdEventFragmentBinding
    private val viewModel: DdEventViewModel by lazy {
        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        ViewModelProvider(
            requireActivity(),
            DdEventViewModel.Factory(dailyDayDao)
        )[DdEventViewModel::class.java]
    }

    /** Module */
    private val ddEventLayoutManager: DdEventLayoutManager by lazy { DdEventLayoutManager(requireContext()) }
    private val mLayoutManager
        get() = ddEventLayoutManager.layoutManager
    private val ddEventListSorter = DdEventListSorter()

    /** Data */
    private var ddEventList: List<DdEvent> = emptyList()
        set(value){
            field = value
            displayEvents()
        }


    private var selectedGroupId
        get() = viewModel.selectedGroupId
        set(value) { viewModel.selectedGroupId = value }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dd_event_fragment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayGroupsPicker()
        displayEventsFromGroupId(groupId = selectedGroupId)

        binding.run {
            this.ui = this@DdEventFragment
        }
    }

    private fun displayGroupsPicker() {
        val displayEventsDdGroupAdapter = DisplayEventsDdGroupAdapter(this, viewModel)
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect() {
                displayEventsDdGroupAdapter.submitList(it)
            }
        }
        binding.groupRecyclerView.run{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = displayEventsDdGroupAdapter
        }
    }

    override fun displayEventsFromGroupId(groupId: Long?) {
        selectedGroupId = when{
            (groupId == null || groupId < 0) -> -1L
            else -> groupId
        }

        lifecycle.coroutineScope.launch {
            when (selectedGroupId) {
                -1L -> viewModel.getAllDdEvent().collect() {
                    ddEventList = it
                }
                else -> viewModel.getDdEventListByGroupId(selectedGroupId).collect() {
                    ddEventList = it
                }
            }
        }
    }
    var ddEventRecyclerViewAdapter = DdEventRecyclerViewAdapter(emptyList(), { onEventItemClicked(it) })
    var mLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    @SuppressLint("NotifyDataSetChanged")
    private fun displayEvents(){

/*        var topOffset: Int
        var firstItem: Int

        mLinearLayoutManager.run{
            firstItem = findFirstVisibleItemPosition()
            val firstItemView = findViewByPosition(firstItem)
            topOffset = firstItemView?.top ?: 0
        }*/

//        val layoutManagerState = mLinearLayoutManager.onSaveInstanceState()

//        ddEventRecyclerViewAdapter.setList(ddEventListSorter.sort(ddEventList))
//        ddEventRecyclerViewAdapter
//
//        mLinearLayoutManager.onRestoreInstanceState(layoutManagerState)

//        mLinearLayoutManager.run{
//            scrollToPositionWithOffset(firstItem, topOffset)
//        }

        val mLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val ddEventRecyclerViewAdapter = DdEventRecyclerViewAdapter(ddEventList, { onEventItemClicked(it) })
//        ddEventRecyclerViewAdapter.setList(ddEventList)
//        ddEventRecyclerViewAdapter.notifyDataSetChanged()

        binding.eventRecyclerView.run{
            layoutManager = mLinearLayoutManager
            adapter = ddEventRecyclerViewAdapter
        }
        when(ddEventList.size){
            0 -> binding.noEventLayout.visibility = View.VISIBLE
            else -> binding.noEventLayout.visibility = View.GONE
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.add -> navigateToAddDdEvent(selectedGroupId)
            R.id.layout -> {
                ddEventLayoutManager.circleLayout()
                displayEvents()
            }
            R.id.sort -> {
                ddEventListSorter.cycleSortMethod()
                displayEvents()
            }
            R.id.group -> navigateToGroupList()
            R.id.noEventLayout -> navigateToAddDdEvent(selectedGroupId)
        }
    }



    private fun onEventItemClicked(ddEvent: DdEvent) {
        navigateToEditScreen(ddEvent.id)
    }

    private fun navigateToGroupList() {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdGroupFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToAddDdEvent(groupId: Long = -1) {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(isAdd = true, groupId = groupId)
        requireView().findNavController().navigate(action)
    }

    private fun navigateToEditScreen(eventId: Long) {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(isAdd = false, eventId = eventId)
        requireView().findNavController().navigate(action)
    }
}