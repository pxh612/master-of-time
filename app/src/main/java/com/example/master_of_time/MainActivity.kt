package com.example.master_of_time

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(MyTimberDebugTree())

        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        binding.bottomNavigationView.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.bottomNavigationView -> {
                Timber.d("Click: reponsed")
            }
        }
    }



}