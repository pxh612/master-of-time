package com.example.master_of_time.screens.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.master_of_time.R
import com.example.master_of_time.databinding.AlarmEditFragmentBinding

class AlarmEditFragment: Fragment() {
    private lateinit var binding : AlarmEditFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.alarm_edit_fragment, container, false)
        return binding.root
    }
}