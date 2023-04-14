package com.example.master_of_time.module.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import timber.log.Timber

class MyAnimator {
    private val SHOW_STATE = 0
    private val HIDE_STATE = 1

    private val DEFAULT_DURATION = 300L


    private var mDuration = DEFAULT_DURATION
    private var state: Int = 0

    private lateinit var _view: View
    var view: View
        get() = _view
        set(value){
            _view = value
            when(state){
                SHOW_STATE -> _view.visibility = View.VISIBLE
                HIDE_STATE -> _view.visibility = View.GONE
            }
        }

    private fun updateState(desiredState: Int): Boolean {
        return when(state){
            desiredState -> false
            else -> {
                state = desiredState
                true
            }
        }
    }

    private fun animateScalingWithAnimator(){

        val LOW_X = 0f
        val HIGH_X = 1f
        val LOW_Y = 0f
        val HIGH_Y = 1f

        val animatorX = when(state) {
            HIDE_STATE -> ObjectAnimator.ofFloat(view, View.SCALE_X, HIGH_X, LOW_X)
            SHOW_STATE -> ObjectAnimator.ofFloat(view, View.SCALE_X, LOW_X, HIGH_X)
            else -> throw IllegalStateException()
        }
        val animatorY = when(state) {
            HIDE_STATE -> ObjectAnimator.ofFloat(view, View.SCALE_Y, HIGH_Y, LOW_Y)
            SHOW_STATE -> ObjectAnimator.ofFloat(view, View.SCALE_Y, LOW_Y, HIGH_Y)
            else -> throw IllegalStateException()
        }


        val animationSet = AnimatorSet()
            .apply{
                playTogether(animatorX, animatorY)
                duration = mDuration

                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        if(state == SHOW_STATE) view.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        if(state == HIDE_STATE) view.visibility = View.GONE
                    }
                })
            }

        animationSet.start()
    }


    // Function to animate view to drag down and fade out
    fun animateViewDisappear(view: View) {
        // Calculate the final y position to drag to
        val finalY = view.y + view.height

        // Create a value animator to animate the view's y property
        val animator = ValueAnimator.ofFloat(view.y, finalY)

        // Set the duration of the animation in milliseconds
        animator.duration = 500

        // Update the view's y property during the animation
        animator.addUpdateListener { animation ->
            view.y = animation.animatedValue as Float
        }

        // Set an animator listener to perform actions on animation start and end
        animator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.visibility = View.GONE
            }
        })

        // Start the animation
        animator.start()
    }


    fun show() {
        if(updateState(SHOW_STATE)) animateScalingWithAnimator()
    }



    fun hide() {
        if(updateState(HIDE_STATE)) animateScalingWithAnimator()
    }
}