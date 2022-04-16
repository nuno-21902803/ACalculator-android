package com.example.acalculator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.acalculator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder


class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private val TAG = CalculatorFragment::class.java.simpleName


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
        return binding.root
    }


    private fun onOperationClick(op: String) {
        //     Toast.makeText(this, op, Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        binding.button1.setOnClickListener { onClickSymbol("1") }
        binding.button2.setOnClickListener { onClickSymbol("2") }
        binding.button3.setOnClickListener { onClickSymbol("3") }
        binding.button4.setOnClickListener { onClickSymbol("4") }
        binding.button5.setOnClickListener { onClickSymbol("5") }
        binding.button6.setOnClickListener { onClickSymbol("6") }
        binding.buttonAdd.setOnClickListener { onClickSymbol("+") }
        binding.buttonDot.setOnClickListener { onClickSymbol(".") }
        binding.buttonEquals.setOnClickListener { onClickEquals() }




        binding.buttonClean.setOnClickListener {
            Log.i(TAG, "Click no botão C")
            binding.textVisor.text = "0"

        }

        binding.buttonBackspace.setOnClickListener {
            Log.i(TAG, "Click no botão B")
            if (binding.textVisor.text.length > 1) {
                binding.textVisor.text =
                    binding.textVisor.text.subSequence(0, binding.textVisor.text.length - 1)

            } else {
                binding.textVisor.text = "0"
            }

        }

    }

    private fun onClickSymbol(s: String) {
        Log.i(TAG, "Click no botão $s")

        if (binding.textVisor.text == "0") {
            binding.textVisor.text = s
        } else {
            binding.textVisor.append(s)
        }
    }

    private fun onClickEquals() {
        Log.i(TAG, "Click no botão =")
        val expression = binding.textVisor.text.toString()
        val expressionBuilder = ExpressionBuilder(
            expression
        ).build()
        val result = expressionBuilder.evaluate().toString()
        binding.textVisor.text = result
        (activity as MainActivity).addOperation(
            OperationUI(
                expression = expression,
                result = result
            )
        )
        Log.i(TAG, "O resultado é $result")
    }
}