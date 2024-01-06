package com.example.mobileexchange.image_classification.domain

import android.graphics.Bitmap

interface BillClassifier {
    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}