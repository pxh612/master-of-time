package com.example.pxh612_trivia_practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pxh612_trivia_practice.Symbols.EQL
import com.example.pxh612_trivia_practice.databinding.FragmentGameBinding
import timber.log.Timber
import kotlin.random.Random

class GameFragment : Fragment(), View.OnClickListener {
    data class Question(
        var equation: String
    )
    private lateinit var binding : FragmentGameBinding


    lateinit var currentQuestion : Question
    val totalQuestionToAsk = 5
    var currentQuestionCount : Int = 0
    var correctAnswerCount : Int = 0
    var correctSum : Int = 0
    var lastCorrectSum : Int = 0
    lateinit var userAnswerString : String
    var userAnswer : Int = 0
    var numberOne : Int = 0
    var numberTwo : Int = 0
    var isFirstQuestion : Boolean = true
    var currentDifficulty : Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game,container,false)

        generateNewQuestionAndDisplay()
        binding.question = this
        binding.submit.setOnClickListener(this)
        return binding.root
    }

    private val INTEGER_LIMIT_CEIL = 500
    private fun generateNewQuestionAndDisplay() {
        generateNewQuestion()
        displayNewQuestion()
    }
    private fun generateNewQuestion() {
        currentQuestionCount++
        numberOne = Random.nextInt(INTEGER_LIMIT_CEIL)
        numberTwo = Random.nextInt(INTEGER_LIMIT_CEIL)
        lastCorrectSum = correctSum
        correctSum = numberOne + numberTwo

    }
    private fun displayNewQuestion() {
        if(isFirstQuestion){
            binding.correctAnswerMessage.visibility = View.INVISIBLE
            binding.yourAnswerMessage.visibility = View.INVISIBLE
        }
        else {
            binding.correctAnswerMessage.visibility = View.VISIBLE
            binding.yourAnswerMessage.visibility = View.VISIBLE
        }
        Timber.i("correctSum" + EQL + correctSum)

        var display_equation = "$numberOne + $numberTwo"
        binding.input.text = null
        currentQuestion = Question(display_equation)
        binding.currentQuestionCount.text = "Question: $currentQuestionCount/$totalQuestionToAsk"
        binding.yourAnswerMessage.text = "Your answer was: $userAnswer"
        binding.correctAnswerMessage.text = "Correct answer: $lastCorrectSum"
        binding.invalidateAll()

        isFirstQuestion = false
    }



    override fun onClick(view: View) {
        when(view.id){
            R.id.submit -> {
                fetchInput()
                if(userAnswerString.isNullOrEmpty()){
                    Toast.makeText(activity,"Your answer is empty!", Toast.LENGTH_SHORT).show()
                }
                else if(checkAnswerIsCorrect()){
                    Timber.d("correct answer")

                    correctAnswerCount++
                    if(correctAnswerCount >= 5) showGameWin()
                    else generateNewQuestionAndDisplay()
                }
                else {
                    Timber.d("wrong answer")
                    showGameOver()
                }
            }
        }
    }

    private fun fetchInput() {
        userAnswerString = binding.input.text.toString()
    }

    private fun showGameOver() {
        requireView().findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
    }

    private fun showGameWin() {
        Timber.e("TODO")
    }

    private fun checkAnswerIsCorrect(): Boolean {
        userAnswer = Integer.parseInt(userAnswerString)
        Timber.i("userAnswer" + EQL + userAnswer)
        return userAnswer == correctSum
    }

}