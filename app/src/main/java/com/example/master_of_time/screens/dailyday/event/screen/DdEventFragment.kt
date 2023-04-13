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
import com.example.master_of_time.databinding.DdEventFragmentBinding
import com.example.master_of_time.screens.dailyday.event.DdEventListAdapter
import com.example.master_of_time.screens.dailyday.event.DdEventLayoutManager
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import com.example.master_of_time.screens.dailyday.group.DisplayEventsDdGroupAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class DdEventFragment : Fragment(), View.OnClickListener, DisplayEventsDdGroupAdapter.Listener {

    private lateinit var binding : DdEventFragmentBinding
    private val viewModel: DdEventViewModel by lazy {
        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        ViewModelProvider(
            requireActivity(),
            DdEventViewModel.Factory(dailyDayDao)
        )[DdEventViewModel::class.java]
    }

    private val ddEventLayoutManager: DdEventLayoutManager by lazy {
        DdEventLayoutManager(requireContext())
    }
    private val ddEventListAdapter = DdEventListAdapter { onEventItemClicked(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dd_event_fragment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val displayEventsDdGroupAdapter = DisplayEventsDdGroupAdapter(this)
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect() {
                displayEventsDdGroupAdapter.submitList(it)
            }
        }

        // TODO: Save state using SharePreferences:
        onDisplayEventsByGroup(groupId = null)



        binding.run {
            this.ui = this@DdEventFragment

            toolbar.setNavigationOnClickListener {  }

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
        when(view.id){
            R.id.add -> navigateToAddDdEvent()
            R.id.layout -> {
                ddEventLayoutManager.changeLayout()
                binding.eventRecyclerView.layoutManager = ddEventLayoutManager.value
            }
            R.id.group -> navigateToGroupList()
            R.id.noEventLayout -> navigateToAddDdEvent()
        }
    }


    override fun onDisplayEventsByGroup(groupId: Long?) {
        lifecycle.coroutineScope.launch {
            when (groupId) {
                null -> viewModel.getAllDdEvent().collect() {
                    pushEventListToAdapter(it)
                }
                else -> viewModel.getDdEventListByGroupId(groupId).collect() {
                    pushEventListToAdapter(it)
                }
            }
        }
    }

    private fun pushEventListToAdapter(list: List<DdEvent>) {
        ddEventListAdapter.submitList(list)
        displayNoEventLayout(list.size)
    }

    fun displayNoEventLayout(listSize: Int) {
        when(listSize){
            0 -> binding.noEventLayout.visibility = View.VISIBLE
            else -> binding.noEventLayout.visibility = View.GONE
        }
    }

    private fun onEventItemClicked(ddEvent: DdEvent) {
        navigateToEditScreen(ddEvent.id)
    }

    private fun navigateToGroupList() {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdGroupFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToAddDdEvent() {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(true)
        requireView().findNavController().navigate(action)
    }

    private fun navigateToEditScreen(eventId: Long) {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(false, eventId)
        requireView().findNavController().navigate(action)
    }
}