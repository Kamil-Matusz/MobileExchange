package com.example.mobileexchange.ui.flashlight

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FlashlightViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashlightViewModel::class.java)) {
            return FlashlightViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}