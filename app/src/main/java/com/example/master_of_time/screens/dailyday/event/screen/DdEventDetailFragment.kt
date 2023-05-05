package com.example.master_of_time.screens.dailyday.event.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.table.DdEventHistory
import com.example.master_of_time.databinding.DdEventDetailFragmentBinding
import com.example.master_of_time.module.dailyday.DdEventCalculation
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModel
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModelFactory
import com.example.master_of_time.screens.dailyday.history.DdEventDetailHistoryAdapter
import com.example.master_of_time.screens.dailyday.history.DdEventHistoryEditFragment
import timber.log.Timber

class DdEventDetailFragment: Fragment(), View.OnClickListener,
    DdEventDetailHistoryAdapter.Listener {
    private lateinit var binding: DdEventDetailFragmentBinding
    private val viewModel: DdEventEditViewModel by lazy {
        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        ViewModelProvider(
            requireActivity(),
            DdEventEditViewModelFactory(dailyDayDao)
        )[DdEventEditViewModel::class.java]
    }


    /** Data */
    private var ddEventId: Long? = null

    private var ddEvent: DdEvent = DdEvent()
        set(value){
            field = value

            /** toolbar */
            binding.toolbar.title = value.title.take(20)
            binding.toolbar.subtitle = "Details"

            /** group */
            ddEventId = value.id
            value.groupId?.let {
                viewModel.getGroupNameByGroupId(it).observe(this.viewLifecycleOwner){ it ->
                    if (it != null) groupName = it
                }
            }

            /** Date */
            val ddEventCalculation = DdEventCalculation(value.calculationTypeId, value.date)
            binding.run {
                title.text = value.title
                pickedDate.text = ddEventCalculation.displayPickedDate()
            }

            /** init View */
//            initEventTimeline()
            initEventHistory()
        }



    private var groupName: String = ""
        set(value){
            field = value

            binding.groupTextView.text = value
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dd_event_detail_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run{
            bindUI = this@DdEventDetailFragment

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            /*eventTimelineRecyclerView.run{
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }*/
        }
        retrieveParentData()
    }


    private fun initEventHistory() {
        val ddEventDetailHistoryAdapter = DdEventDetailHistoryAdapter(this)
        binding.eventHistoryRecyclerView.run{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ddEventDetailHistoryAdapter
        }
        viewModel.getDdEventHistoriesOfEventId(ddEvent.id).observe(viewLifecycleOwner){
            ddEventDetailHistoryAdapter.submitList(it)
            when(it.size){
                0 -> {
                    binding.emptyEventHistoryMessage.visibility = View.VISIBLE
                }
                else ->  {
                    binding.emptyEventHistoryMessage.visibility = View.GONE
                }
            }
        }
    }

    private fun retrieveParentData() {
        val navigationArgs: DdEventDetailFragmentArgs by navArgs()

        val id = navigationArgs.eventId
        if(id < 0) throw Exception("eventId is null")

        viewModel.getDdEvent(id)?.observe(this.viewLifecycleOwner) {
            when(it) {
                null -> findNavController().popBackStack()
                else -> ddEvent = it
            }
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.editButton -> {
                navigateEditEvent()
            }
            R.id.addHistoryButton -> {
                navigateAddEventHistory()
            }
        }
    }
    override fun onDdEventHistoryClicked(ddEventHistory: DdEventHistory) {
        navigateEditEventHistory(ddEventHistory)
    }

    private fun navigateEditEventHistory(ddEventHistory: DdEventHistory) {
        if(ddEventId == null){
            Timber.i("ddEvent is null!")
            return
        }
        val action = DdEventDetailFragmentDirections.actionDdEventDetailFragmentToDdEventHistoryEditFragment(
            fragmentIntent = DdEventHistoryEditFragment.EDIT_INTENT,
            eventHistoryId = ddEventHistory.id,
            eventId = ddEvent.id,
        )
        requireView().findNavController().navigate(action)
    }


    private fun navigateAddEventHistory() {
        if(ddEventId == null){
            Timber.i("ddEvent is null!")
            return
        }
        val action = DdEventDetailFragmentDirections.actionDdEventDetailFragmentToDdEventHistoryEditFragment(
            fragmentIntent = DdEventHistoryEditFragment.ADD_INTENT,
            eventId = ddEvent.id
        )
        requireView().findNavController().navigate(action)
    }

    private fun navigateEditEvent() {
        if(ddEventId == null){
            Timber.i("ddEvent is null!")
            return
        }
        val action = DdEventDetailFragmentDirections.actionDdEventDetailFragmentToDdEventEditFragment(isAdd = false, eventId = ddEvent.id)
        requireView().findNavController().navigate(action)
    }



}