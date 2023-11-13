package com.example.mobileexchange.ui.exchange_calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConversionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Hello, this is Conversion Fragment"
    }
    val text: LiveData<String> = _text

    // Dodaj tutaj właściwości LiveData, które będą przechowywać dane potrzebne do przeliczeń
    // Dodaj metody do obsługi przeliczeń i aktualizacji danych w ViewModelu
}
