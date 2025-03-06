package com.jatin.practiseandroid

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.jatin.practiseandroid.databinding.ActivityDrawerBinding
import com.jatin.practiseandroid.ui.home.HomeFragment

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val homeFragment = HomeFragment()
        setSupportActionBar(binding.appBarDrawer.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val age = intent.getStringExtra("age")
        val bloodGroup = intent.getStringExtra("blood")

        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("email", email)
        bundle.putString("age", age)
        bundle.putString("blood", bloodGroup)
        homeFragment.arguments = bundle
        Log.d("Name",name.toString())
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_drawer,homeFragment)
            .commit();
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}