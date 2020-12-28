package com.nadhif.moviecatalogue.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.catalogueFragment, R.id.favoriteFragment ->
                    binding.bottomNavigation.visibility = View.VISIBLE
                R.id.movieDetailFragment, R.id.tvShowDetailFragment ->
                    binding.bottomNavigation.visibility = View.GONE
            }
        }
    }
}