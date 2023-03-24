package com.example.master_of_time.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.databinding.FragmentTitleBinding

class TitleFragment : Fragment(), View.OnClickListener {
    private lateinit var binding : FragmentTitleBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container,false)
        binding.playButton.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.playButton -> {
                view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
            }
        }
    }

}

