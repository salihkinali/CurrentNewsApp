package com.salihkinali.currentnewsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.salihkinali.currentnewsapp.R
import com.salihkinali.currentnewsapp.databinding.ActivityMainBinding
import com.salihkinali.currentnewsapp.util.visible

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CurrentNewsApp)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)
        navHostFragment.findNavController().run {
            binding.materialToolbar.setupWithNavController(this, AppBarConfiguration(graph))
        }
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

    }

    private fun hideBottomNavigation() {
        binding.bottomNavigation.visible(false)
    }

    private fun showBottomNavigation() {
        binding.bottomNavigation.visible(true)
    }

}