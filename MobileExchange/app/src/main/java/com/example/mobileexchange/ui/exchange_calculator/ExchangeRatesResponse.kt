package com.example.mobileexchange.ui.exchange_calculator

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("rates")
    val rates: Map<String, Double>?
)

