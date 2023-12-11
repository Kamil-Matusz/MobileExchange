package com.example.mobileexchange.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome in the Mobile Exchange\n\n" +
                "The application is a mobile currency exchange office with the ability\n to convert currencies " +
                "and find currency exchange offices in your area." +
                "\n\n\n" +
                "If you have questions write to email:\n\n exchangecantor@cantor.com"
    }
    val text: LiveData<String> = _text
}