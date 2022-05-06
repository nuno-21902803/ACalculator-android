package com.example.acalculator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.acalculator.databinding.FragmentCalculatorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder


class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel
    private var adapter =
        HistoryAdapter(onClick = ::onOperationClick, onLongClick = ::onOperationLongClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.app_name)
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        binding = FragmentCalculatorBinding.bind(view)

        //associar este fragmento ao viewmodel
        viewModel = ViewModelProvider(this).get(
            CalculatorViewModel::class.java
        )
        binding.textVisor.text = viewModel.getDisplayValue()

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        binding.button1.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("1") }
        binding.button2.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("2") }
        binding.button3.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("3") }
        binding.button4.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("4") }
        binding.button5.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("5") }
        binding.button6.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("6") }
        binding.buttonAdd.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("+")
        }
        binding.buttonDot.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol(".")
        }
        binding.buttonClean.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("C")
        }
        binding.buttonBackspace.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("B")
        }
        binding.buttonEquals.setOnClickListener {
            onClickEquals()
        }

    }


    private fun onClickEquals() {
        val displayUpdated = viewModel.onClickEquals {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getHistory {
                    updateHistory(it.map {
                        OperationRoom(
                            it.uuid,
                            it.expression,
                            it.result
                        )
                    })
                }
            }
        }
        binding.textVisor.text = displayUpdated
    }

    private fun updateHistory(operations: List<OperationRoom>) {
        val history =
            operations.map { OperationUI(it.uuid, it.expression, it.result, it.timestamp) }
        CoroutineScope(Dispatchers.Main).launch {
            adapter.updateItems(history)
        }
    }

    private fun onOperationClick(operationUi: OperationUI) {
        NavigationManager.goToOperationDetail(parentFragmentManager, operationUi)
    }

    private fun onOperationLongClick(operation: OperationUI): Boolean {
        /*
        Toast.makeText(context, getString(R.string.deleting), Toast.LENGTH_SHORT).show()
        viewModel.onDeleteOperation(operation.uuid) { viewModel.onGetHistory { updateHistory(it) } }
         */
        return false
    }
}