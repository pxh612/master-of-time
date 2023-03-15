package com.example.pxh612_trivia_practice.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber
import kotlin.random.Random

class GameViewModel : ViewModel(){
    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 10000L

    }

    /** Time setting */
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    private val timer: CountDownTimer


    /** Game setting */
    val totalRound = 1

    /** Ongoing game's status */
    private val _isGameWin = MutableLiveData<Boolean>()
    val isGameWin: LiveData<Boolean>
        get() = _isGameWin

    private val _isGameLose = MutableLiveData<Boolean>()
    val isGameLose: LiveData<Boolean>
        get() = _isGameLose

    private val _isRoundWin = MutableLiveData<Boolean>()
    val isRoundWin: LiveData<Boolean>
        get() = _isRoundWin

    var totalRoundWin : Int = 0
    var currentQuestionCount = 0
    var correctAnswerCount : Int = 0

    /** Question setting */
    private val INTEGER_LIMIT_CEIL = 500
    var currentDifficulty : Int = 1
    var correctSum : Int = 0
    var lastCorrectSum : Int = 0
    var numberOne : Int = 0
    var numberTwo : Int = 0

    /** User input */
    lateinit var userAnswerString : String
    var userAnswer : Int = 0


    init{
        Timber.v("GameViewModel created.")

        _isGameWin.value = false
        _isGameLose.value = false
        generateNewQuestion()

        /** Timer init  */
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ONE_SECOND
                Timber.d("currentTimeString = $currentTimeString")
            }

            override fun onFinish() {
                _currentTime.value = DONE
                Timber.d("currentTimeString = $currentTimeString")
                onTimeOver()
            }
        }

        timer.start()
        Timber.d("currentTimeString = $currentTimeString")
    }

    private fun onTimeOver() {
        _isGameWin.value = false
        _isGameLose.value = true
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("GameViewModel destroyed.")
    }


    internal fun generateNewQuestion() {
        currentQuestionCount++
        numberOne = Random.nextInt(INTEGER_LIMIT_CEIL)
        numberTwo = Random.nextInt(INTEGER_LIMIT_CEIL)
        lastCorrectSum = correctSum
        correctSum = numberOne + numberTwo
        Timber.i("correctSum = $correctSum")
        Timber.i("currentQuestionCount = $currentQuestionCount")
    }
    internal fun processGame(){
        if(checkAnswerisCorrect()){
            _isRoundWin.value = true
            totalRoundWin++
            if(totalRoundWin == totalRound) _isGameWin.value = true
            else generateNewQuestion()
        }
        else{
            _isRoundWin.value = false
        }
    }
    internal fun checkAnswerisCorrect(): Boolean {
        Timber.i("userAnswer = $userAnswer")
        return userAnswer == correctSum
    }

    fun isFirstQuestion(): Boolean {
        return currentQuestionCount == 1
    }

    fun takeUserInput(userAnswerString: String) {
        userAnswer = Integer.parseInt(userAnswerString)
    }
}