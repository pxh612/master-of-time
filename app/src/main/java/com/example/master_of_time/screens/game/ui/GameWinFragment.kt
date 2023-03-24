package com.example.master_of_time.screens.game.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.databinding.FragmentGameWinBinding


class GameWinFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentGameWinBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_win,container,false)

        binding.backToHomeScreen.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.back_to_home_screen -> {
                navigateTitleScreen()
            }
        }
    }

    private fun navigateTitleScreen() {
        requireView().findNavController().navigate(R.id.action_gameWinFragment_to_titleFragment)
    }
}