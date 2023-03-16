package com.example.pxh612_trivia_practice.screens.game.UIcontroller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pxh612_trivia_practice.R
import com.example.pxh612_trivia_practice.databinding.FragmentGameBinding
import com.example.pxh612_trivia_practice.screens.game.GameViewModel
import com.example.pxh612_trivia_practice.screens.game.GameViewModelFactory

class GameFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentGameBinding

    /** ViewModel */
    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        /** init DataBiding */
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        /** init ViewModel */
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.gameViewModel = viewModel

        /** init View */
        displayQuestion()
        binding.submit.setOnClickListener(this)

        /**  LiveData Observer */
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.isGameWin.observe(viewLifecycleOwner, Observer { isGameWin ->
            if(isGameWin){
                showGameWin()
            }
        })
        viewModel.isGameLose.observe(viewLifecycleOwner, Observer { isGameLose ->
            if(isGameLose){
                showGameOver()
            }
        })

        return binding.root
    }

    private fun displayQuestion() {
        binding.input.text = null
        binding.invalidateAll()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.submit -> {
                val userInputString = binding.input.text.toString()
                Log.d("HuyPX", "onClick: " )

                if (userInputString.isEmpty()) {
                    notifyEmptyAnswer()
                } else{
                    viewModel.takeUserInput(binding.input.text.toString())
                    viewModel.processGame()
                    displayQuestion()
                }
            }
        }
    }

    private fun notifyEmptyAnswer() {
        val message = "Your answer is empty!"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showGameOver() {
        requireView().findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
    }

    private fun showGameWin() {
        requireView().findNavController().navigate(R.id.action_gameFragment_to_gameWinFragment)
    }


}