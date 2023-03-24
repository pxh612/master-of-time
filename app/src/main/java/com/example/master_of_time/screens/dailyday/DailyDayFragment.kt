package com.example.master_of_time.screens.dailyday

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.master_of_time.R
import com.example.master_of_time.database.dailyday.DailyDayDatabase
import com.example.master_of_time.database.dailyday.DailyDayRepository
import com.example.master_of_time.database.dailyday.OfflineDailyDayRepository
import com.example.master_of_time.databinding.FragmentDailyDayBinding
import timber.log.Timber

class DailyDayFragment : Fragment() {

    companion object {
        fun newInstance() = DailyDayFragment()
    }
    private lateinit var binding : FragmentDailyDayBinding
    private lateinit var viewModel: DailyDayViewModel
    private lateinit var dailyDayRepository: DailyDayRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_daily_day,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** init Repository? */
        // Currently failing
         dailyDayRepository = OfflineDailyDayRepository(DailyDayDatabase.getInstance(requireContext()).dailyDayDao())
        Timber.v("...init Repository...")


        /** init ViewModel */
//        Timber.e("Problem: viewmodel crashed")
        // Problem: viewmodel crashed
        // Test: no passing Repos - good
        // Reason: can not pass arg with ViewModelProvider
        // Solution: ViewModelFactory or Dagger


        // Attempt 1: factory
        viewModel = ViewModelProvider(this, DailyDayViewModelFactory(dailyDayRepository))[DailyDayViewModel::class.java]

        // Attemp 2: dagger...


//        viewModel = ViewModelProvider(this, DailyDayViewModelFactory())
//        viewModel = ViewModelProvider(requireActivity())[DailyDayViewModel::class.java]
//        viewModel = ViewModelProvider(requireActivity())[DailyDayViewModel(dailyDayRepository)::class.java]

        /** init View */
        binding.message.text = "from DailyDayFragment"
    }
}