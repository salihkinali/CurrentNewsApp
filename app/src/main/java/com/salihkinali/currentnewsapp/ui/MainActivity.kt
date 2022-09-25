package com.salihkinali.currentnewsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.salihkinali.currentnewsapp.R
import com.salihkinali.currentnewsapp.databinding.ActivityMainBinding
import com.salihkinali.currentnewsapp.util.visible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CurrentNewsApp)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setSupportActionBar(binding.materialToolbar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)

        // Setup the ActionBar with navController and 4 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchingFragment,
                R.id.favoriteFragment,
                R.id.settingFragment
            )
        )
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newDetailFragment -> {
                    hideBottomNavigation()
                    binding.materialToolbar.title = "Detail Screen"
                }
                R.id.webViewFragment -> {
                    hideBottomNavigation()
                    binding.materialToolbar.title = "WebView Screen"
                }

                else -> {
                    showBottomNavigation()
                    binding.materialToolbar.title = "Current News"
                }
            }
        }
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigation.visible(false)
    }

    private fun showBottomNavigation() {
        binding.bottomNavigation.visible(true)
    }

}