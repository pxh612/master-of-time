package com.example.master_of_time.screens.game.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.databinding.FragmentGameOverBinding
import com.example.master_of_time.screens.game.GameViewModel
import timber.log.Timber


class GameOverFragment : Fragment(), OnClickListener {

    private lateinit var binding : FragmentGameOverBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_over, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        Timber.d("Value: current question count = ${viewModel.currentQuestionCount} & ${viewModel.currentQuestionCountDisplay.value}")

        binding.retry.setOnClickListener(this)
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