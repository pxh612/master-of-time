package com.example.master_of_time.screens.dailyday.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.R
import com.example.master_of_time.databinding.FragmentDailyDayEditBinding
import com.example.master_of_time.screens.dailyday.DailyDayViewModel
import com.example.master_of_time.screens.dailyday.DailyDayViewModelFactory
import timber.log.Timber


class DailyDayEditFragment : Fragment() {

    private lateinit var viewModel: DailyDayViewModel
    private lateinit var binding: FragmentDailyDayEditBinding
    private val navigationArgs: DailyDayEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyDayEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** retrieve Navigation */
        val id = navigationArgs.dailyDayId

        /** init ViewModel */
//        viewModel = ViewModelProvider(this, DailyDayViewModelFactory(dailyDayRepository))[DailyDayViewModel::class.java]


    }
}