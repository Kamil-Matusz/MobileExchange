package com.example.mobileexchange.ui.exchange_calculator

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/latest.json")
    fun getExchangeRates(@Query("app_id") appId: String): Call<ExchangeRatesResponse>
}
