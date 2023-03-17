package com.example.pxh612_trivia_practice.screens.game.UIcontroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pxh612_trivia_practice.R
import com.example.pxh612_trivia_practice.databinding.FragmentGameOverBinding
import com.example.pxh612_trivia_practice.screens.game.GameViewModel
import timber.log.Timber


class GameOverFragment : Fragment(), OnClickListener {

    private lateinit var binding : FragmentGameOverBinding
    /** ViewModel */
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /** init DataBiding */
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_over,
            container,
            false
        )

        /** init ViewModel */
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.gameViewModel = viewModel
        Timber.d("Value: current question count = ${viewModel.currentQuestionCount} & ${viewModel.currentQuestionCountDisplay.value}")

        /** init Buttons */
        binding.retry.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.retry -> {
                restartGame()
            }
        }
    }

    private fun restartGame() {
        requireView().findNavController().navigate(R.id.action_gameOverFragment_to_gameFragment)
    }
}