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
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.R
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.DdEventFragmentBinding
import com.example.master_of_time.module.animation.MyAnimator
import com.example.master_of_time.module.dailyday.DdEventListSorter
import com.example.master_of_time.screens.dailyday.event.DdEventLayoutManager
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventRecyclerViewAdapter
import com.example.master_of_time.screens.dailyday.group.DisplayEventsDdGroupAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class DdEventFragment : Fragment(), View.OnClickListener, DisplayEventsDdGroupAdapter.Listener{

    /** Architecture */
    private lateinit var binding : DdEventFragmentBinding
    private val viewModel: DdEventViewModel by lazy {
        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        Timber.d("viewModel created: not fast enough?")
        ViewModelProvider(
            requireActivity(),
            DdEventViewModel.Factory(dailyDayDao)
        )[DdEventViewModel::class.java]
    }

    /** View */
    private val displayEventsDdGroupAdapter by lazy {  DisplayEventsDdGroupAdapter(this, viewModel) }
    var ddEventRecyclerViewAdapter = DdEventRecyclerViewAdapter(emptyList(), { onEventItemClicked(it) })
    var mLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    /** Module */
    private val ddEventLayoutManager: DdEventLayoutManager by lazy { DdEventLayoutManager(requireContext()) }
    private val ddEventListSorter = DdEventListSorter()
    private val addMyAnimator: MyAnimator
        get() = viewModel.addMyAnimator

    /** Data */
    private var ddEventList: List<DdEvent> = emptyList()
        set(value){
            field = value
            displayEvents()
        }

    private var selectedGroupId
        get() = viewModel.selectedGroupId
        set(value) { viewModel.selectedGroupId = value }

    private val mLayoutManager
        get() = ddEventLayoutManager.layoutManager



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dd_event_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("verbose: onViewCreated")

        fetchForGroupAdapter()
        fetchForEventAdapter(groupId = selectedGroupId)

        binding.run {
            this.ui = this@DdEventFragment

            groupRecyclerView.run{
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = displayEventsDdGroupAdapter
            }
            eventRecyclerView.run{
                layoutManager = mLayoutManager
                adapter = ddEventRecyclerViewAdapter
            }
        }

        addMyAnimator.view = binding.add
        initOnScrollListener()
    }

    private fun initOnScrollListener() {
        var absoluteY = binding.eventRecyclerView.computeHorizontalScrollOffset()
        Timber.d("absoluteY = $absoluteY")
        val onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                absoluteY = binding.eventRecyclerView.computeVerticalScrollOffset()
                when{
                    (absoluteY <= 300) -> addMyAnimator.show()
                    else -> addMyAnimator.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState != 0)
            }
        }
        binding.eventRecyclerView.addOnScrollListener(onScrollListener)
    }

    private fun fetchForGroupAdapter() {
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect() {
                displayEventsDdGroupAdapter.submitList(it)
            }
        }
    }

    override fun fetchForEventAdapter(groupId: Long?) {
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

    @SuppressLint("NotifyDataSetChanged")
    private fun displayEvents(){
        /** Restore ScrollPosition using saveInstanceState */
        val layoutManagerState = mLayoutManager.onSaveInstanceState()
        ddEventRecyclerViewAdapter.setList(ddEventListSorter.sort(ddEventList))
        ddEventRecyclerViewAdapter.notifyDataSetChanged()
        mLayoutManager.onRestoreInstanceState(layoutManagerState)

        binding.eventRecyclerView.layoutManager = mLayoutManager


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