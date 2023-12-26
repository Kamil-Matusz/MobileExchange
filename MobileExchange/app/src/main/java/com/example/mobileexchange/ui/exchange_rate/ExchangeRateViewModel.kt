// ExchangeRateViewModel.kt

package com.example.mobileexchange.ui.exchange_rate

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat

class ExchangeRateViewModel(application: Application) : AndroidViewModel(application) {

    private val _exchangeRates = MutableLiveData<Currency>()
    val exchangeRates: LiveData<Currency> = _exchangeRates

    // Dummy data for testing
    private val dummyRates = Currency(1.0, 0.75, 0.85, 1.2, 1.1, 6.5, 110.0, 110.0, 47.7, 20.0)

    init {
        // Tutaj można dodać kod do sprawdzenia dostępności internetu
        if (isNetworkAvailable()) {
            getExchangeRates()
        } else {
            showToast("No internet connection.")
        }
    }

    fun getExchangeRates() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://openexchangerates.org/api/latest.json?app_id=c78e1185375b457bbc16d9e262fb0a56")
                val connection = url.openConnection() as HttpURLConnection

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputSystem = connection.inputStream
                    val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                    val request = Gson().fromJson(inputStreamReader, Request::class.java)
                    val roundedRates = Currency(
                        request.rates.USD.roundToTwoDecimal(),
                        request.rates.GBP.roundToTwoDecimal(),
                        request.rates.EUR.roundToTwoDecimal(),
                        request.rates.CAD.roundToTwoDecimal(),
                        request.rates.CHF.roundToTwoDecimal(),
                        request.rates.CNY.roundToTwoDecimal(),
                        request.rates.JPY.roundToTwoDecimal(),
                        request.rates.RUB.roundToTwoDecimal(),
                        request.rates.BRL.roundToTwoDecimal(),
                        request.rates.INR.roundToTwoDecimal(),
                    )

                    _exchangeRates.postValue(roundedRates)
                    inputStreamReader.close()
                    inputSystem.close()
                }
            } catch (e: Exception) {
                // Obsługa błędów pobierania kursów
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun Double.roundToTwoDecimal(): Double {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(this).toDouble()
    }
}
