package com.example.pxh612_trivia_practice.screens.d_day

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pxh612_trivia_practice.R
import com.example.pxh612_trivia_practice.database.daily_day.DailyDayDatabase
import com.example.pxh612_trivia_practice.database.daily_day.DailyDayRepository
import com.example.pxh612_trivia_practice.database.daily_day.OfflineDailyDayRepository
import com.example.pxh612_trivia_practice.screens.game.GameViewModel
import timber.log.Timber

class DailyDayFragment : Fragment() {

    companion object {
        fun newInstance() = DailyDayFragment()
    }

    private lateinit var viewModel: DailyDayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** init Repository? */
        val dailyDayRepository : DailyDayRepository by lazy {
            OfflineDailyDayRepository(DailyDayDatabase.getInstance(requireContext()).dailyDayDao())
        }


        /** init ViewModel */
        Timber.e("viewmodel crashed")
//        viewModel
//        viewModel = ViewModelProvider(requireActivity())[DailyDayViewModel(dailyDayRepository)::class.java]
    }
}