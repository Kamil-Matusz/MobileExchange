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

        // Observe the LiveData in the ViewModel
        exchangeRateViewModel.exchangeRates.observe(viewLifecycleOwner) { currency ->
            updateUI(currency)
        }

        // Trigger the network request to get the exchange rates
        exchangeRateViewModel.getExchangeRates()

        return root
    }

    private fun updateUI(currency: Currency) {
        // Update UI with the exchange rates
        binding.usd.text = "USD: ${currency.USD}"
        binding.gbp.text = "GBP: ${currency.GBP}"
        binding.eur.text = "EUR: ${currency.EUR}"
        binding.cad.text = "CAD: ${currency.CAD}"
        binding.chf.text = "CHF: ${currency.CHF}"
        binding.cny.text = "CNY: ${currency.CNY}"
        binding.jpy.text = "JPY: ${currency.JPY}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
