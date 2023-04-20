package com.example.master_of_time.screens.dailyday.event.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.databinding.DdEventDetailFragmentBinding
import com.example.master_of_time.module.dailyday.DdEventCalculation
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModel
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModelFactory
import timber.log.Timber

class DdEventDetailFragment: Fragment(), View.OnClickListener{
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

            ddEventId = value.id
            value.groupId?.let {
                viewModel.getGroupNameByGroupId(it).observe(this.viewLifecycleOwner){ it ->
                    if (it != null) groupName = it
                }
            }
            val ddEventCalculation = DdEventCalculation(value.calculationTypeId, value.date)
            binding.run {
                title.text = value.title
                pickedDate.text = ddEventCalculation.displayPickedDate()
            }
            initEventTimeline()
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

    private fun retrieveParentData() {
        val navigationArgs: DdEventDetailFragmentArgs by navArgs()

        val id = navigationArgs.eventId
        if(id < 0) throw Exception("NULL eventId")

        viewModel.getDdEvent(id).observe(this.viewLifecycleOwner) {
            ddEvent = it
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

    private fun initEventTimeline(){
        /*val ddEventCalculation = DdEventCalculation(ddEvent)
        var dateList = mutableListOf<String>()
        var dateDistanceList = mutableListOf<String>()

        for(i in 5 downTo 1){
            val dateJumped = ddEventCalculation.minus(i.toLong())
            val displayString = DdEventCalculation(date = dateJumped).displayDayDistanceFromPresent()
            dateList.add(dateJumped.toDateFormat())
            dateDistanceList.add(displayString.toString())
//            Timber.d("i=$i & result = ${dateJumped.toDateFormat()} & display = $displayString")
        }
        for(i in 0..5){
            val dateJumped = ddEventCalculation.plus(i.toLong())
            val displayString = DdEventCalculation(date = dateJumped).displayDayDistanceFromPresent()
            dateList.add(dateJumped.toDateFormat())
            dateDistanceList.add(displayString.toString())
//            Timber.d("i=$i & result = ${dateJumped.toDateFormat()} & display = $displayString")
        }
        binding.eventTimelineRecyclerView.adapter = DdEventTimelineAdapter(dateList, dateDistanceList)*/
    }


    private fun navigateAddEventHistory() {
        if(ddEventId == null){
            Timber.i("ddEvent is null!")
            return
        }
        val action = DdEventDetailFragmentDirections.actionDdEventDetailFragmentToDdEventHistoryEditFragment(eventHistoryId = -1, eventId = ddEvent.id)
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