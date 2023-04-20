package com.example.master_of_time

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.master_of_time.databinding.ActivityMainBinding
import com.example.master_of_time.module.dailyday.DdEventListSorter
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
        /*val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.navigation)*/

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val bottomNavSelectedItemId = sharedPreferences
            .getInt("bottomNavSelectedItemId", DdEventListSorter.DEFAULT_SORT_METHOD)


        binding.bottomNav.run {
            setupWithNavController(navController)
            selectedItemId = bottomNavSelectedItemId
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            /** write data to SharePreferences */
            sharedPreferences.edit().putInt("bottomNavSelectedItemId", destination.id).apply()

        }

    }
}
