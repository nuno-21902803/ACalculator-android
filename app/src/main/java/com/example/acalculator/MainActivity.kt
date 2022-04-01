package com.example.acalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.health.TimerStat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acalculator.databinding.ActivityMainBinding
import com.example.acalculator.databinding.ItemExpressionBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import kotlin.math.exp
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    var historico = mutableListOf<OperationUI>()


    //private val adapter = HistoryAdapter(::onOperationClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "metodo onCreate invocado")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        Log.i(TAG, "metodo onDestroy invocado")
        super.onDestroy()
    }
/*
    private fun onOperationClick(op: String) {
        Toast.makeText(this, op, Toast.LENGTH_LONG).show()
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

        binding.rvHistoric?.layoutManager = LinearLayoutManager(this)
        binding.rvHistoric?.adapter = adapter

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


        val operationUI = OperationUI(binding.textVisor.text.toString(), expression.evaluate().toString(), timestamp)
        historico.add(operationUI)
        adapter.updateItems(historico)
        binding.textVisor.text = expression.evaluate().toString()

        Log.i(TAG, "O result é ${binding.textVisor.text}")

    }*/
}

class OperationUI(
    val expression: String,
    val result: String,
    val timeStamp: Timestamp
){

    @SuppressLint("SimpleDateFormat")
    override fun toString(): String {
        return "Exp :$expression' = '$result," +
                " ${SimpleDateFormat("dd-MM-yyyy' - T'HH:mm:ss").format(timeStamp)})"
    }

}


class HistoryAdapter(
    private val onOperationClick: (String) -> Unit,
    private var items: List<OperationUI> = listOf()
    ) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemExpressionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onOperationClick(items[position].timeStamp.toString())
        }

        holder.binding.textExpression.text = items[position].expression
        holder.binding.textResult.text = items[position].result
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<OperationUI>) {
        this.items = items
        notifyDataSetChanged()
    }
}