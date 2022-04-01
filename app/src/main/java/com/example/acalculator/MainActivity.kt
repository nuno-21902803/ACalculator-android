package com.example.acalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.acalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    var historico = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.button1.setOnClickListener { onClickSymbol("1") }
        binding.button1.setOnClickListener { onClickSymbol("2") }
        binding.button1.setOnClickListener { onClickSymbol("3") }
        binding.button1.setOnClickListener { onClickSymbol("4") }
        binding.button1.setOnClickListener { onClickSymbol("5") }
        binding.button1.setOnClickListener { onClickSymbol("6") }
        binding.button1.setOnClickListener { onClickSymbol("+") }
        binding.button1.setOnClickListener { onClickSymbol(".") }
        binding.button1.setOnClickListener { onClickSymbol("0") }
        binding.button1.setOnClickListener { onClickSymbol("1") }
        binding.button1.setOnClickListener { onClickEquals() }




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

        if(binding.textVisor.text == "0"){
            binding.textVisor.text = s
        } else{
            binding.textVisor.append(s)
        }
    }

    private fun onClickEquals() {
        Log.i(TAG, "Click no botão =")

        val expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()

        val op = "${binding.textVisor.text} = ${expression.evaluate()}"
        historico.add(op)
        binding.textVisor.text = expression.evaluate().toString()
        Log.i(TAG, "O result é ${binding.textVisor.text}")
    }
}