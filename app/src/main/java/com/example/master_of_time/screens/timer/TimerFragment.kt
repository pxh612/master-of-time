package com.example.master_of_time.screens.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.master_of_time.R
import com.example.master_of_time.databinding.AlarmFragmentBinding
import com.example.master_of_time.databinding.TimerFragmentBinding

class TimerFragment: Fragment(){
    private lateinit var binding : TimerFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.timer_fragment, container, false)
        return binding.root
    }
}