package com.example.master_of_time.screens.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.master_of_time.R
import com.example.master_of_time.databinding.ActivityMainBinding
import com.example.master_of_time.databinding.AlarmFragmentBinding
import com.example.master_of_time.screens.dailyday.event.screen.DdEventFragmentDirections

class AlarmFragment: Fragment(), View.OnClickListener {

    private lateinit var binding : AlarmFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.alarm_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run{
            bindUI = this@AlarmFragment
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addButton -> navigateAlarmEditFragment()
        }
    }

    private fun navigateAlarmEditFragment() {
        val action = AlarmFragmentDirections.actionAlarmFragmentToAlarmEditFragment()
        requireView().findNavController().navigate(action)
    }
}