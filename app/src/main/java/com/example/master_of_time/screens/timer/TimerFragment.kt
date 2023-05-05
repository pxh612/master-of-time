package com.example.master_of_time.screens.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.databinding.AlarmFragmentBinding
import com.example.master_of_time.databinding.DdEventEditFragmentBinding
import com.example.master_of_time.databinding.TimerFragmentBinding

class TimerFragment: Fragment(), View.OnClickListener {
    private lateinit var binding : TimerFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.timer_fragment, container, false)
        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addButton -> navigateTimerEditFragment()
        }
    }

    private fun navigateTimerEditFragment() {
        val action = TimerFragmentDirections.actionTimerFragmentToTimerEditFragment(TimerEditFragment.ADD_INTENT)
        requireView().findNavController().navigate(action)
    }
}