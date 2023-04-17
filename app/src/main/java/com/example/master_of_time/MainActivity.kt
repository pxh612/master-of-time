package com.example.master_of_time

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.ActivityMainBinding
import com.example.master_of_time.screens.be.BeFragmentDirections
import com.example.master_of_time.screens.dailyday.event.screen.DdEventFragmentDirections
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

        binding.bottomNav.setOnItemSelectedListener {
            Timber.d("MenuItem = $it")
            when(it.title){
                "be" -> {
                    Timber.d("Reponse: click be")
                    val action = DdEventFragmentDirections.actionDdEventFragmentToBeFragment3()
                    Navigation.findNavController(this, R.id.myNavHostFragment).navigate(action)
//                    findNavController().navigate(action)
                    /*val action = DdEventFragmentDirections.actionDdEventFragmentToDdGroupFragment()
                    requireView().findNavController().navigate(action)*/
                }
                "Event" -> {
                    Timber.d("Reponse: click Event")
                    val action = BeFragmentDirections.actionBeFragment3ToDdEventFragment()
                    Navigation.findNavController(this, R.id.myNavHostFragment).navigate(action)
                    /*val action = DdEventFragmentDirections.actionDdEventFragmentToDdGroupFragment()
                    requireView().findNavController().navigate(action)*/
                }
            }
            true
        }


        /*val navView: BottomNavigationView = binding.bottomNav

        val navController = findNavController(R.id.myNavHostFragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.beFragment,
                R.id.beFragment,
                R.id.ddEventFragment,
                R.id.beFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/


//        setupBottomNavigationBar()
    }

/*
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNav
        val navGraphIds = listOf(R.navigation.navigation_be, R.navigation.navigation_daily_day)

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.myNavHostFragment,
            intent = intent
        )

//
//        // Setup the bottom navigation view with a list of navigation graphs
//        val controller = bottomNavigationView.setupWithNavController(
//            navGraphIds = navGraphIds,
//            fragmentManager = supportFragmentManager,
//            containerId = R.id.nav_host_fragment,
//            intent = intent
//        )
//
//        // Whenever the selected controller changes, setup the action bar.
//        controller.observe(this, Observer { navController ->
//            setupActionBarWithNavController(navController)
//        })
//        currentNavController = controller
    }

*/


}