package com.cuncisboss.moneymanagerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncisboss.moneymanagerapp.R
import com.cuncisboss.moneymanagerapp.databinding.ItemDashboardBinding
import com.cuncisboss.moneymanagerapp.model.ExpenseModel

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    private var expenseList = arrayListOf<ExpenseModel>()

    inner class DashboardViewHolder(val binding: ItemDashboardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_dashboard,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = expenseList.size

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.binding.expense = expenseList[position]
    }

    fun submitList(newExpenseList: List<ExpenseModel>) {
        expenseList.clear()
        expenseList.addAll(newExpenseList)
        notifyDataSetChanged()
    }
}