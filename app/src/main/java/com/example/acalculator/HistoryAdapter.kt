package com.example.acalculator

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acalculator.databinding.ItemExpressionBinding

class HistoryAdapter(
    private val onClick: (OperationUI) -> Unit,
    private val onLongClick: (OperationUI) -> Boolean,
    private var items: List<OperationUI> = listOf(),
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemExpressionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        if (items.isNotEmpty()) {
            holder.itemView.setOnClickListener { onClick(items[position]) }
            holder.itemView.setOnLongClickListener { onLongClick(items[position]) }

            holder.binding.textExpression.text = items[position].expression
            holder.binding.textResult.text = items[position].result.toString()
            holder.binding.textUuid.text = "UUID:${items[position].uuid}"
        }
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<OperationUI>) {
        this.items = items
        notifyDataSetChanged()
    }
}