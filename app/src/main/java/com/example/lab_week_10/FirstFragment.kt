package com.example.lab_week_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

class FirstFragment : Fragment() {

    private lateinit var viewModel: TotalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(TotalViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Start observing the LiveData
        observeViewModel()
    }

    private fun observeViewModel() {
        // Observe the LiveData object from the ViewModel
        viewModel.total.observe(viewLifecycleOwner, { total ->
            // Update the UI when the LiveData changes
            updateText(total)
        })
    }

    private fun updateText(total: Int) {
        // Safely access the TextView and update its text
        view?.findViewById<TextView>(R.id.text_total)?.text =
            getString(R.string.text_total, total)
    }

    companion object {
        // This method can be simplified if you don't need parameters
        fun newInstance() = FirstFragment()
    }
}
