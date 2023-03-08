package com.example.pxh612_trivia_practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pxh612_trivia_practice.databinding.FragmentTitleBinding
import timber.log.Timber

class TitleFragment : Fragment(), View.OnClickListener {
    private lateinit var binding : FragmentTitleBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title,container,false)
        binding.playButton.setOnClickListener(this)
        Timber.d("create title fragment...")
        return binding.root
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.play_button -> {
                view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
            }
            else -> {

            }
        }
    }

}

