package com.example.acalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.acalculator.databinding.FragmentHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_OPERATIONS = "param1"

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: CalculatorViewModel
    private var history: List<OperationUI>? = null
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            history = it.getParcelableArrayList(ARG_OPERATIONS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.history)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter = HistoryAdapter(parentFragmentManager)
        binding.rvHistoric.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric.adapter = adapter
        viewModel.getHistory { updateHistory(it) }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    private fun updateHistory(operations: List<OperationUI>) {
        val history = operations
        CoroutineScope(Dispatchers.Main).launch {
            showHistory(history.isNotEmpty())
            adapter.updateItems(history)
        }
    }

    private fun showHistory(show: Boolean) {
        if (show) {
            binding.rvHistoric.visibility = View.VISIBLE
        } else {
            binding.rvHistoric.visibility = View.GONE
        }
    }

    private fun onOperationClick(operation: OperationUI) {
        NavigationManager.goToOperationDetail(parentFragmentManager, operation)
    }

    private fun onOperationLongClick(operation: OperationUI): Boolean {
        Toast.makeText(context, "Deleting Operation", Toast.LENGTH_SHORT).show()
        viewModel.deleteOperation(operation.uuid) { viewModel.getHistory { updateHistory(it) } }
        return false
    }
}