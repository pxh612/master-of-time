package com.example.master_of_time.screens.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.master_of_time.MyApplication
import com.example.master_of_time.R
import com.example.master_of_time.databinding.AlarmFragmentBinding

class AlarmFragment: Fragment(), View.OnClickListener {

    private lateinit var binding : AlarmFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.alarm_fragment, container, false)

        if(MyApplication.WORKING_MODE) return binding.root
        else return inflater.inflate(R.layout.unimplemented_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Toolbar?>(R.id.toolbar).apply {
            title = "Alarm"
        }

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