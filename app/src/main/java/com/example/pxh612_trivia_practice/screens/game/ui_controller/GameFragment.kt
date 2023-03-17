package com.example.pxh612_trivia_practice.screens.game.ui_controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pxh612_trivia_practice.R
import com.example.pxh612_trivia_practice.databinding.FragmentGameBinding
import com.example.pxh612_trivia_practice.screens.game.GameViewModel
import timber.log.Timber

class GameFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentGameBinding

    /** ViewModel */
    private lateinit var viewModel: GameViewModel
//    private var viewModel: GameViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        /** inflate view with DataBinding */
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
//        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
//        viewModel = activity.run(ViewModelProviders.of(this).get(GameViewModel::class.java))
        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)


        /** Button */
        binding.submit.setOnClickListener(this)

        /** init DataBiding */
        binding.apply {
            gameFragment = this@GameFragment
            gameViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }


        /**  LiveData Observer */
        viewModel.isGameWin.observe(viewLifecycleOwner) { isGameWin ->
            if(isGameWin) {
                showGameWin()
            }
        }
        viewModel.isGameLose.observe(viewLifecycleOwner) { isGameLose ->
            if(isGameLose){
                showGameOver()
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

    private fun showGameOver() {
        requireView().findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
    }

    private fun showGameWin() {
        requireView().findNavController().navigate(R.id.action_gameFragment_to_gameWinFragment)
    }


}

