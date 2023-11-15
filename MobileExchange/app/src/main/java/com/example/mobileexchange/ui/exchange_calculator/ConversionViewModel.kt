package com.example.mobileexchange.ui.exchange_calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConversionViewModel : ViewModel() {

    private val _exchangeRates = MutableLiveData<Map<String, Double>>()
    val exchangeRates: LiveData<Map<String, Double>> = _exchangeRates

    init {
        // Tutaj można dodać kod do pobierania kursów walut
        // W tym przykładzie używam Retrofit do komunikacji z API

        val retrofit = Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.getExchangeRates("c78e1185375b457bbc16d9e262fb0a56")

        call.enqueue(object : Callback<ExchangeRatesResponse> {
            override fun onResponse(call: Call<ExchangeRatesResponse>, response: Response<ExchangeRatesResponse>) {
                if (response.isSuccessful) {
                    val rates = response.body()?.rates
                    _exchangeRates.postValue(rates)
                }
            }

            override fun onFailure(call: Call<ExchangeRatesResponse>, t: Throwable) {
                // Obsługa błędów pobierania kursów
            }
        })
    }

    fun convertAmount(amount: Double, currencyFrom: String, currencyTo: String): Double {
        val rates = _exchangeRates.value

        // Sprawdź, czy mamy kursy walut
        if (rates != null) {
            // Przelicz kwotę
            val rateFrom = rates[currencyFrom] ?: 1.0
            val rateTo = rates[currencyTo] ?: 1.0

            return amount * rateTo / rateFrom
        }

        // Zwróć kwotę bez przeliczania, jeśli nie mamy kursów walut
        return amount
    }
}
