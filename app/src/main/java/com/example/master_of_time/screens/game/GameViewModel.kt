package com.example.master_of_time.screens.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.master_of_time.module.task.MathQuestion
import timber.log.Timber

class GameViewModel : ViewModel(){
    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 10 * ONE_SECOND
    }



    /** Time setting */
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    private var timer: CountDownTimer


    /** Game setting */
    val totalRound = 10

    /** Ongoing game's status */
    private lateinit var mathQuestion : MathQuestion
    var totalRoundWin : Int = 0
    var currentQuestionCount = 0

    /** Ongoing game's status - LiveData to UI_controller */
    private val _isGameWin = MutableLiveData<Boolean>()
    val isGameWin: LiveData<Boolean>
        get() = _isGameWin
    private val _isGameLose = MutableLiveData<Boolean>()
    val isGameLose: LiveData<Boolean>
        get() = _isGameLose
    var answerHintDisplay : String? = null /** Warning: this is updated through binding.invalidateAll() */

    /** Ongoing game's status - LiveData to XML_layout */
    private val _currentQuestionDisplay = MutableLiveData<String>()
    val currentQuestionDisplay: LiveData<String>
        get() = _currentQuestionDisplay

    private val _currentQuestionCountDisplay = MutableLiveData<String>()
    val currentQuestionCountDisplay: LiveData<String>
        get() = _currentQuestionCountDisplay

    /** Question setting */
    var currentDifficulty : Int = 1


    /** User input */
    var userAnswer : Int = 0


    init{
        Timber.v("enter...")

        /** init Timer   */
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ONE_SECOND
            }
            override fun onFinish() {
                _currentTime.value = DONE
                onTimeOver()
            }
        }

        /** start Game */
        startNewRound()
    }

    private fun startNewTimer() {
        Timber.v("enter...")

        timer.cancel()
        timer.start()
    }

    private fun onTimeOver() {
        _isGameWin.value = false
        _isGameLose.value = true
    }

    override fun onCleared() {
        Timber.v("enter...")

        super.onCleared()
    }

    internal fun startNewRound() {
        Timber.v("enter...")

        /** update status */
        currentQuestionCount++
        startNewTimer()

        /** generate technical question */
        currentDifficulty = (currentQuestionCount-1)/2+1
        mathQuestion = MathQuestion(currentDifficulty)

        /** LiveData display */
        _currentQuestionDisplay.value = mathQuestion.getEquationString()
        _currentQuestionCountDisplay.value = "Question: $currentQuestionCount/$totalRound"
    }
    internal fun processGame(){
        if(mathQuestion.isLowerThanCorrectAnswer(userAnswer)) {
            answerHintDisplay = "Higher!"
        } else if(mathQuestion.isHigherThanCorrectAnswer(userAnswer)){
            answerHintDisplay = "Lower!"
        } else if(mathQuestion.isEqualToCorrectAnswer(userAnswer)){
            answerHintDisplay = null

            totalRoundWin++
            if(totalRoundWin == totalRound) _isGameWin.value = true
            else startNewRound()
        }
    }

    fun takeUserInput(userAnswerString: String) {
        userAnswer = Integer.parseInt(userAnswerString)
    }

    fun onClickButtonTest(){
        Timber.v("enter...")

        TODO("android:onClick worked with GameViewModel but not GameFragment")
    }
}