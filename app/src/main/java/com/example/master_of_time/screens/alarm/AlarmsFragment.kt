package com.example.master_of_time.screens.alarm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.master_of_time.R
import com.example.master_of_time.databinding.FragmentAlarmsBinding


class AlarmsFragment : Fragment() {

    private lateinit var binding : FragmentAlarmsBinding
    private lateinit var viewModel : AlarmsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        /** inflate view with DataBinding */
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_alarms,
            container,
            false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** init ViewModel */
        viewModel = ViewModelProvider(this).get(AlarmsViewModel::class.java)

        /** init RecyclerView - Layout Manager */
        val manager = GridLayoutManager(activity, 2)
//        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup()

        /** init RecyclerView - Adapter */
//        val adapter = AlarmAdapter()

        /** init DataBinding */
        binding.apply {
            alarmListViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner

            /** RecycleView */
//            alarmRecycleView.adapter = adapter
            alarmRecycleView.layoutManager = manager
        }
    }


}