package com.example.master_of_time

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class MainActivity : AppCompatActivity(){
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(MyTimberDebugTree())

        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        initBottomNavigationView()
    }


    private fun initBottomNavigationView(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        /*val sharedPreferences = getSharedPreferences("LAST_SESSION", MODE_PRIVATE)
        val bottomNavigationViewSelectedItemId = sharedPreferences
            .getInt("bottomNavigationViewSelectedItemId", R.id.alarmFragment)

        Timber.d("bottomNavigationViewSelectedItemId = $bottomNavigationViewSelectedItemId")
        Timber.d("${R.id.alarmFragment} && ${R.id.ddEventFragment}")*/

        binding.bottomNav.run {
            setupWithNavController(navController)
            selectedItemId = R.id.ddEventFragment // this is dumb
        }

    }
}
