package com.example.acalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.acalculator.databinding.FragmentHistoryBinding

private const val ARG_PARAM1 = "param1"

class HistoryFragment : Fragment() {

    private var operations: ArrayList<OperationUI>? = null
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operations = it.getParcelableArrayList(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.history)

        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding
    }

    companion object {
        @JvmStatic
        fun newInstance(message: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, message)
                }
            }
    }
}