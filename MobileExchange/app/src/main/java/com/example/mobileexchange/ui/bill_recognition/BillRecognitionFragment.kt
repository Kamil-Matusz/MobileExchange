package com.example.mobileexchange.ui.bill_recognition

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileexchange.R

class BillRecognitionFragment : Fragment() {

    companion object {
        fun newInstance() = BillRecognitionFragment()
    }

    private lateinit var viewModel: BillRecognitionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bill_recognition, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BillRecognitionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}