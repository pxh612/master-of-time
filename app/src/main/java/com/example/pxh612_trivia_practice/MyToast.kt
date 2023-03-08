package com.example.pxh612_trivia_practice

import android.widget.Toast
import androidx.fragment.app.FragmentActivity

class MyToast{
    companion object {
        fun notifyNotYetImplement(context: FragmentActivity?){
            val message = "Not yet implemented..."
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

}