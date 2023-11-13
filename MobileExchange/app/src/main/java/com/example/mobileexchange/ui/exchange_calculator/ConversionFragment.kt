package com.example.mobileexchange.ui.exchange_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileexchange.databinding.FragmentConversionBinding

class ConversionFragment : Fragment() {

    private var _binding: FragmentConversionBinding? = null
    private val binding get() = _binding!!

    private lateinit var conversionViewModel: ConversionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        conversionViewModel = ViewModelProvider(this).get(ConversionViewModel::class.java)

        _binding = FragmentConversionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inicjalizacja UI, np. ustawienie adapterów dla spinnerów, dodanie obsługi zdarzeń, itp.

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
