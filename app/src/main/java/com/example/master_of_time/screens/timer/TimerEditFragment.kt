package com.example.master_of_time.screens.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master_of_time.R
import com.example.master_of_time.databinding.TimerEditFragmentBinding
import com.example.master_of_time.databinding.TimerFragmentBinding

class TimerEditFragment : Fragment(){

    companion object{
        val ADD_INTENT = 0
    }

    private lateinit var binding : TimerEditFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.timer_edit_fragment, container, false)
    }




}