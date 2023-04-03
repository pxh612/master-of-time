package com.example.master_of_time.screens.dailyday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.dailyday.OfflineDailyDayRepository
import com.example.master_of_time.databinding.FragmentDailyDayGroupBinding
import com.example.master_of_time.screens.dailyday.DailyDayLayoutManager
import com.example.master_of_time.screens.dailyday.adapter.DailyDayAdapter
import com.example.master_of_time.screens.dailyday.viewmodel.DailyDayViewModel
import com.example.master_of_time.screens.dailyday.viewmodel.DailyDayViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class DailyDayGroupFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDailyDayGroupBinding
    private lateinit var dialogFragment: DailyDayGroupEditDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_daily_day_group,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        /** init Custom Classes */
//        dailyDayRepository = OfflineDailyDayRepository(AppDatabase.getInstance(requireContext()).dailyDayDao())
//        dailyDayLayoutManager = DailyDayLayoutManager(requireContext())
//
//
//        /** init ViewModel */
//        viewModel = ViewModelProvider(
//            requireActivity(),
//            DailyDayViewModelFactory(dailyDayRepository)
//        )[DailyDayViewModel::class.java]


//        /** init Adapter for RecyclerView*/
//        val dailyDayAdapter = DailyDayAdapter { DailyDay -> onAdapterClicked(DailyDay) }
//        lifecycle.coroutineScope.launch {
//            viewModel.getAllDailyDay().collect() {
//                Timber.v("> collect FlowList for adapter: size = ${it.size}")
//                dailyDayAdapter.submitList(it)
//            }
//        }


        /** init View */
        binding.run {

//            recylerView.run {
//                layoutManager = dailyDayLayoutManager.value
//                adapter = dailyDayAdapter
//            }

//            header.run {
//                add.setOnClickListener(this@DailyDayFragment)
//                layout.setOnClickListener(this@DailyDayFragment)
//                buttonOne.setOnClickListener(this@DailyDayFragment)
//            }

            // test: Databinding
            add.setOnClickListener(this@DailyDayGroupFragment)
            back.setOnClickListener(this@DailyDayGroupFragment)

        }
    }

    override fun onClick(view: View) {
        Timber.v("> reponsive click")
        when (view.id) {
            R.id.back -> findNavController().popBackStack()
            R.id.add -> showDialog()
        }
    }

    private fun showDialog() {
        Timber.v("> clicked to show dialogFragment")
        val action = DailyDayGroupFragmentDirections.actionDailyDayGroupFragmentToDailyDayGroupEditDialogFragment()
        requireView().findNavController().navigate(action)
    }

    fun testClick(view: View){
        Timber.d("> databinding onclick")
    }
    fun testClickNoArgument(){
        Timber.d("> databinding onclick testClickNoArgument")
    }

}
