package com.example.mobileexchange

import android.app.DownloadManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileexchange.R.id.action_currencyRates
import com.example.mobileexchange.R.id.action_exchangeCalculator
import com.example.mobileexchange.R.id.action_flashlight
import com.example.mobileexchange.R.id.action_maps
import com.example.mobileexchange.R.id.action_coordinates
import com.example.mobileexchange.R.id.action_billRecognition
import com.example.mobileexchange.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_flashlight,R.id.nav_exchange_rates,R.id.nav_exchange_calculator,R.id.nav_maps
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            action_flashlight -> {
                findNavController(R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nav_flashlight)
                return true
            }
            action_currencyRates -> {
                findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_exchange_rates)
                return true
            }
            action_exchangeCalculator -> {
                findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_exchange_calculator)
                return true
            }
            action_maps -> {
                findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_maps)
                return true
            }
            action_coordinates -> {
                findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_coordinates)
                return true
            }
            action_billRecognition -> {
                findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_bill_recognition)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}