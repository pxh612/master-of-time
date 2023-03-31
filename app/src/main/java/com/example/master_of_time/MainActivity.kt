package com.example.master_of_time

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import com.example.master_of_time.R
import com.example.master_of_time.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(MyTimberDebugTree())
        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Timber.d("> activity saved")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d("> activity restored")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return super.onRetainCustomNonConfigurationInstance()
        Timber.d("> onRetainCustomNonConfigurationInstance")
    }

}