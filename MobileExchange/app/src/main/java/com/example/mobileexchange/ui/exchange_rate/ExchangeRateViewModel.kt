// ExchangeRateViewModel.kt

package com.example.mobileexchange.ui.exchange_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExchangeRateViewModel : ViewModel() {

    private val _exchangeRates = MutableLiveData<Currency>()
    val exchangeRates: LiveData<Currency> = _exchangeRates

    // Dummy data for testing
    private val dummyRates = Currency(1.0, 0.75, 0.85, 1.2, 1.1, 6.5, 110.0)

    fun getExchangeRates() {
        // In a real app, you would perform a network request here to get the latest exchange rates
        // For simplicity, I'm using dummy data
        GlobalScope.launch(Dispatchers.Main) {
            _exchangeRates.value = dummyRates
        }
    }
}
