package com.example.mobileexchange.ui.exchange_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileexchange.R
import com.example.mobileexchange.ui.exchange_calculator.ConversionViewModel

class ConversionFragment : Fragment() {

    private lateinit var conversionViewModel: ConversionViewModel
    private lateinit var editTextAmount: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        conversionViewModel = ViewModelProvider(this).get(ConversionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_conversion, container, false)

        // Inicjalizacja referencji do elementów interfejsu użytkownika
        editTextAmount = root.findViewById(R.id.editTextAmount)
        spinnerFrom = root.findViewById(R.id.spinner_from)
        spinnerTo = root.findViewById(R.id.spinner_to)
        buttonConvert = root.findViewById(R.id.buttonConvert)
        textViewResult = root.findViewById(R.id.textViewResult)

        // Ustawienie adaptera dla spinnerów
        val currenciesArray = resources.getStringArray(R.array.currencies)

        // Adapter dla pierwszego spinnera
        val adapterFrom = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currenciesArray)
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapterFrom

        // Adapter dla drugiego spinnera
        val adapterTo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currenciesArray)
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTo.adapter = adapterTo

        // Przykładowa obsługa przycisku "Convert"
        buttonConvert.setOnClickListener {
            // Pobierz wartości z UI
            val amountStr = editTextAmount.text.toString()
            val currencyFrom = spinnerFrom.selectedItem.toString()
            val currencyTo = spinnerTo.selectedItem.toString()

            if (amountStr.isNotEmpty()) {
                // Konwertuj wartość na Double
                val amount = amountStr.toDouble()

                // Wywołaj funkcję przeliczania w ViewModelu
                val result = conversionViewModel.convertAmount(amount, currencyFrom, currencyTo)

                // Wyświetl wynik na interfejsie użytkownika
                textViewResult.text = getString(R.string.result_text, result, currencyTo)
            } else {
                // Wyświetl komunikat o błędzie, gdy pole kwoty jest puste
                Toast.makeText(requireContext(), "Please enter an amount.", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
}
