package com.example.mobileexchange.ui.flashlight

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileexchange.R
import com.example.mobileexchange.databinding.FragmentFlashlightBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class FlashlightFragment : Fragment() {

    private var _binding: FragmentFlashlightBinding? = null
    private val binding get() = _binding!!

    private lateinit var flashlightViewModel: FlashlightViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlashlightBinding.inflate(inflater, container, false)
        val root: View = binding.root

        flashlightViewModel = ViewModelProvider(this, FlashlightViewModelFactory(requireContext()))
                .get(FlashlightViewModel::class.java)

        binding.imgTorch.setOnClickListener {
            flashlightViewModel.toggleTorchState()
        }

        flashlightViewModel.torchState.observe(viewLifecycleOwner) { torchState ->
            updateUI(torchState)
        }

        Dexter.withContext(requireContext()).withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        // Do nothing, permission is granted
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(requireContext(), "Please grant the camera permission", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            p0: PermissionRequest?,
                            p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }
                }).check()

        return root
    }

    private fun updateUI(torchState: Boolean) {
        // Update UI based on torch state
        if (torchState) {
            binding.imgTorch.setImageResource(R.drawable.flashlight_on)
        } else {
            binding.imgTorch.setImageResource(R.drawable.flashlight_off)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}