package com.example.mobileexchange.ui.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class FlashlightViewModel(private val context: Context) : ViewModel() {

    private val _torchState = MutableLiveData<Boolean>().apply {
        value = false
    }
    val torchState: LiveData<Boolean> = _torchState

    private lateinit var cameraManager: CameraManager
    private var camId: String = "0"

    init {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        camId = cameraManager.cameraIdList[0]
    }

    fun toggleTorchState() {
        _torchState.value = !_torchState.value!!

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val torchMode = _torchState.value!!
            cameraManager.setTorchMode(camId, torchMode)
        }
    }
}