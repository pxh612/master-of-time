package com.example.pxh612_trivia_practice.module.question

import timber.log.Timber
import kotlin.random.Random

class MathQuestion(difficulty : Int) {

    var numberOne : Int = 0
    var numberTwo : Int = 0
    var correctAnswer : Int = 0
        get() = numberOne + numberTwo

    /** Difficulty-dependent */
    var integerLimit : Int = 10

    init{
        when(difficulty){
            1 -> integerLimit = 20
            2 -> integerLimit = 100
            3 -> integerLimit = 500
            4 -> integerLimit = 2000
            5 -> integerLimit = 5000
            6 -> integerLimit = 10000
        }
        numberOne = Random.nextInt(integerLimit)
        numberTwo = Random.nextInt(integerLimit)
        Timber.i("Value: correctAnswer = $correctAnswer")
    }

    fun isLowerThanCorrectAnswer(userAnswer: Int): Boolean {
        return userAnswer < correctAnswer
    }
    fun isHigherThanCorrectAnswer(userAnswer: Int): Boolean {
        return userAnswer > correctAnswer
    }
    fun isEqualToCorrectAnswer(userAnswer: Int): Boolean {
        return userAnswer == correctAnswer
    }
    fun getEquationString(): String? {
        return "$numberOne + $numberTwo"
    }


}