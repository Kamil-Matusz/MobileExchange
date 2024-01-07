package com.example.mobileexchange.ui.bill_recognition

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.camera.core.TorchState
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.mobileexchange.R
import com.example.mobileexchange.image_classification.data.TfLiteBillClassifier
import com.example.mobileexchange.image_classification.domain.Classification
import com.example.mobileexchange.image_classification.presentation.BillImageAnalyzer
import com.example.mobileexchange.image_classification.presentation.CameraPreview
import com.example.mobileexchange.image_classification.ui.BillRecognitionTensorflowTheme

class BillRecognitionFragment : Fragment() {

    companion object {
        fun newInstance() = BillRecognitionFragment()
    }

    private lateinit var viewModel: BillRecognitionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // Inside the BillRecognitionFragment's Compose content
                var isFlashOn by remember { mutableStateOf(false) } // State to track flashlight status
                BillRecognitionTensorflowTheme {
                    val applicationContext = context
                    var classifications by remember {
                        mutableStateOf(emptyList<Classification>())
                    }
                    val analyzer = remember {
                        BillImageAnalyzer(
                            classifier = TfLiteBillClassifier(
                                context = applicationContext
                            ),
                            onResults = {
                                classifications = it
                            }
                        )
                    }
                    val controller = remember {
                        LifecycleCameraController(applicationContext).apply {
                            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                            setImageAnalysisAnalyzer(
                                ContextCompat.getMainExecutor(applicationContext),
                                analyzer
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CameraPreview(controller, Modifier.fillMaxSize())

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                        ) {
                            classifications.forEach {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        // Green square stroke in the center
                        Box(
                            modifier = Modifier
                                .size(480.dp, 240.dp)
                                .align(Alignment.Center)
                                .background(Color.Transparent)
                                .border(4.dp, Color.Green)
                        )


                        // Crosshair
                        Box(
                            modifier = Modifier
                                .size(480.dp) // Size of the crosshair
                                .align(Alignment.Center)
                        ) {
                            // Horizontal line
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp) // Thickness of the horizontal line
                                    .background(Color.Green) // Color of the horizontal line
                                    .align(Alignment.Center)
                            )

                            // Vertical line
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.5f)
                                    .width(2.dp) // Thickness of the vertical line
                                    .background(Color.Green) // Color of the vertical line
                                    .align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp) // Adjust padding as needed
                        ) {
                            Button(
                                onClick = {
                                    // Toggle flashlight status
                                    isFlashOn = !isFlashOn

                                    // Access the camera controller and toggle flashlight
                                    controller.cameraInfo?.torchState?.value?.let { torchState ->
                                        controller.cameraControl?.enableTorch(torchState == TorchState.OFF)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(text = if (isFlashOn) "Turn the flashlight off" else "Turn the flashlight on")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BillRecognitionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}