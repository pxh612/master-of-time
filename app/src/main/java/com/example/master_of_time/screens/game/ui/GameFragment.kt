package com.example.master_of_time.screens.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.databinding.FragmentGameBinding
import com.example.master_of_time.screens.game.GameViewModel
import timber.log.Timber

class GameFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentGameBinding

    /** ViewModel */
    private lateinit var viewModel: GameViewModel
//    private var viewModel: GameViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("enter...")

        /** init ViewModel */
        viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]


        /** init Views && Buttons */
        binding.apply {
            gameViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            gameFragment = this@GameFragment
            submit.setOnClickListener(this@GameFragment)
        }

        /** observeLiveData */
        viewModel.run {
            isGameWin.observe(viewLifecycleOwner) { isGameWin ->
                if (isGameWin) {
                    navigateToGameWin()
                }
            }
            isGameLose.observe(viewLifecycleOwner) { isGameLose ->
                if (isGameLose) {
                    navigateToGameOver()
                }
            }
        }
    }


    override fun onClick(view: View) {
        Timber.v("enter...")

        when(view.id){
            R.id.submit -> {
                val userInputString = binding.input.text.toString()

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

    private fun displayQuestion() {
        binding.input.text = null
        binding.invalidateAll()
    }

    private fun notifyEmptyAnswer() {
        val message = "Your answer is empty!"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToGameOver() {
        requireView().findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
    }

    private fun navigateToGameWin() {
        requireView().findNavController().navigate(R.id.action_gameFragment_to_gameWinFragment)
    }


}


