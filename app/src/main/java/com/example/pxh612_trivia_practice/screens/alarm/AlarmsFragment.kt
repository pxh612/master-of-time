package com.example.pxh612_trivia_practice.screens.alarm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pxh612_trivia_practice.R
import com.example.pxh612_trivia_practice.databinding.FragmentAlarmsBinding
import timber.log.Timber


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