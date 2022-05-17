package com.clockworkclyde.imagesearchapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.clockworkclyde.imagesearchapp.R
import com.clockworkclyde.imagesearchapp.databinding.ActivityMainBinding
import com.clockworkclyde.imagesearchapp.ui.feed.FeedFragmentDirections
import com.clockworkclyde.imagesearchapp.ui.gallery.GalleryFragmentDirections
import com.clockworkclyde.imagesearchapp.ui.saved.SavedGalleryFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var directions: NavDirections? = null
            when (item.itemId) {
                R.id.feedFragment -> {
                    directions = FeedFragmentDirections.actionGlobalFeedFragment()
                }
                R.id.galleryFragment -> {
                    directions = GalleryFragmentDirections.actionGlobalSearchFragment()
                }
                R.id.savedGalleryFragment -> {
                    directions = SavedGalleryFragmentDirections.actionGlobalSavedFragment()
                }
            }
            if (directions != null) {
                findNavController(R.id.nav_host_fragment_main).navigate(directions, NavOptions.Builder().setLaunchSingleTop(true).build())
            }
            true
        }
    }
}