// ExchangeRateViewModel.kt

package com.example.mobileexchange.ui.exchange_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ExchangeRateViewModel : ViewModel() {

    private val _exchangeRates = MutableLiveData<Currency>()
    val exchangeRates: LiveData<Currency> = _exchangeRates

    // Dummy data for testing
    private val dummyRates = Currency(1.0, 0.75, 0.85, 1.2, 1.1, 6.5, 110.0, 110.0)

    fun getExchangeRates() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://openexchangerates.org/api/latest.json?app_id=c78e1185375b457bbc16d9e262fb0a56")
                val connection = url.openConnection() as HttpURLConnection

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputSystem = connection.inputStream
                    val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                    val request = Gson().fromJson(inputStreamReader, Request::class.java)
                    _exchangeRates.postValue(request.rates)
                    inputStreamReader.close()
                    inputSystem.close()
                }
            } catch (e: Exception) {
            }
        }
    }
}