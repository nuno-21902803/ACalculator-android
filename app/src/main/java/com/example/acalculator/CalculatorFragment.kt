package com.example.acalculator

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.acalculator.databinding.ActivityMainBinding
import com.example.acalculator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.sql.Timestamp


class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private val TAG = CalculatorFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        // Inflate the layout for this fragment
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

        binding.rvHistoric?.layoutManager = LinearLayoutManager(activity as Context)
       // binding.rvHistoric?.adapter = adapter

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

        val expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()

        val timestamp = Timestamp(System.currentTimeMillis())


        val operationUI = OperationUI(
            binding.textVisor.text.toString(),
            expression.evaluate().toString(),
            timestamp
        )
//        historico.add(operationUI)
  //      adapter.updateItems(historico)
        binding.textVisor.text = expression.evaluate().toString()

        Log.i(TAG, "O result é ${binding.textVisor.text}")

    }
}

object NavigarionManager{

    private fun placeFragment(fm: FragmentManager, fragment: Fragment){
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToCalculatorFragment(fm: FragmentManager){
        placeFragment(fm, CalculatorFragment())
    }
}