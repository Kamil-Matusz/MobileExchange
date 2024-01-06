package com.example.mobileexchange.image_classification.presentation

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.mobileexchange.image_classification.domain.BillClassifier
import com.example.mobileexchange.image_classification.domain.Classification

class BillImageAnalyzer (
     private val classifier: BillClassifier,
     private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if(true) {
            val rotationDegrees = image.imageInfo.rotationDegrees

            val originalBitmap: Bitmap = image.toBitmap() // Assuming 'image' is your original Bitmap

            val matrix = Matrix()
            matrix.postRotate(90f) // Rotate by 90 degrees clockwise

            val bitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, true)
// 'rotatedBitmap' now contains the vertically rotated bitmap

            Log.i("LOG", "${bitmap.height} x ${bitmap.width}")

            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }

         image.close()
    }
}