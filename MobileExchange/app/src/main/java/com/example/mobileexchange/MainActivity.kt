package com.example.mobileexchange

import android.app.DownloadManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import com.example.mobileexchange.R.id.nav_exchange_calculator
import com.example.mobileexchange.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Exchange Calculator", Snackbar.LENGTH_LONG)
                .setAction("Go to Calculator") {
                    findNavController(R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nav_exchange_calculator)
                }.show()
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null) {
            Snackbar.make(binding.root, "Brak dostępu do czujnika natężenia światła", Snackbar.LENGTH_LONG)
                .setAction("OK", null).show()
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
                    .navigate(nav_exchange_calculator)
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
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lightLevel = event.values[0]
            adjustBrightness(lightLevel)
        }
    }

    private fun adjustBrightness(lightLevel: Float) {
        val layoutParams = window.attributes
        layoutParams.screenBrightness = lightLevel / SensorManager.LIGHT_SUNLIGHT_MAX
        window.attributes = layoutParams
    }
}