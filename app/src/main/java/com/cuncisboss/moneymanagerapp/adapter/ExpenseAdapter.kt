package com.cuncisboss.moneymanagerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncisboss.moneymanagerapp.R
import com.cuncisboss.moneymanagerapp.databinding.ItemExpenseBinding
import com.cuncisboss.moneymanagerapp.model.ExpenseModel

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private var expenseList = arrayListOf<ExpenseModel>()

    private var listener: ((expense: ExpenseModel) -> Unit)? = null

    inner class ExpenseViewHolder(val binding: ItemExpenseBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_expense,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = expenseList.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.binding.expense = expenseList[position]
        holder.itemView.setOnClickListener {
            listener?.invoke(expenseList[position])
        }
    }

    fun submitList(newExpenseList: List<ExpenseModel>) {
        expenseList.clear()
        expenseList.addAll(newExpenseList)
        notifyDataSetChanged()
    }

    fun setListener(listener: (expense: ExpenseModel) -> Unit) {
        this.listener = listener
    }
}