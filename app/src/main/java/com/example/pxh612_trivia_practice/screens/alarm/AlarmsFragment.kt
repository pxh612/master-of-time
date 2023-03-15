package com.example.pxh612_trivia_practice.screens.alarm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pxh612_trivia_practice.R
import com.example.pxh612_trivia_practice.databinding.FragmentAlarmsBinding
import timber.log.Timber


class AlarmsFragment : Fragment() {

    private lateinit var binding : FragmentAlarmsBinding
    private lateinit var viewModel : AlarmsViewModel
    private lateinit var adapter : AlarmAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        /** init DataBinding */
        Timber.d("GameFragment creating...")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_alarms,
            container,
            false
        )

        /** init ViewModel */
        viewModel = ViewModelProvider(this).get(AlarmsViewModel::class.java)
        binding.alarmListViewModel = viewModel

        /** init Adapter */
        adapter = AlarmAdapter(AlarmListener {
            val message = "AlarmAdapter listened"
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        })


        return binding.root
    }
}