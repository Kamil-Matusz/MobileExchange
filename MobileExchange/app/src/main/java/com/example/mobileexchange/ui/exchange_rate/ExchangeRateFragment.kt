// ExchangeRateFragment.kt

package com.example.mobileexchange.ui.exchange_rate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileexchange.databinding.FragmentCurrencyRatesBinding

class ExchangeRateFragment : Fragment() {

    private var _binding: FragmentCurrencyRatesBinding? = null
    private val binding get() = _binding!!

    private lateinit var exchangeRateViewModel: ExchangeRateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyRatesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        exchangeRateViewModel = ViewModelProvider(this).get(ExchangeRateViewModel::class.java)
        exchangeRateViewModel.exchangeRates.observe(viewLifecycleOwner) { currency ->
            updateUI(currency)
        }
        exchangeRateViewModel.getExchangeRates()

        return root
    }

    private fun updateUI(currency: Currency) {
        // Update UI with the exchange rates
        binding.gbp.text = "British Pound Sterling (GBP): ${currency.GBP} $"
        binding.eur.text = "Euro (EUR): ${currency.EUR} $"
        binding.cad.text = "Canadian Dolar (CAD): ${currency.CAD} $"
        binding.chf.text = "Swiss Franc (CHF): ${currency.CHF} $"
        binding.cny.text = "Chinese Yuan (CNY): ${currency.CNY} $"
        binding.jpy.text = "Yen (JPY): ${currency.JPY} $"
        binding.rub.text = "Russian Ruble (RUB): ${currency.RUB} $"
        binding.brl.text = "Brazilian Real (BRL): ${currency.BRL} $"
        binding.inr.text = "Indian rupee (INR): ${currency.INR} $"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
