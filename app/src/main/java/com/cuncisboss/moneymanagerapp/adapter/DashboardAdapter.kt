package com.cuncisboss.moneymanagerapp.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncisboss.moneymanagerapp.R
import com.cuncisboss.moneymanagerapp.databinding.ItemDashboardBinding
import com.cuncisboss.moneymanagerapp.databinding.ItemMoreBinding
import com.cuncisboss.moneymanagerapp.model.ExpenseModel
import com.cuncisboss.moneymanagerapp.ui.Navigator
import com.cuncisboss.moneymanagerapp.util.Constants.LIMIT_ITEM
import com.cuncisboss.moneymanagerapp.util.Constants.TAG

class DashboardAdapter(val type: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var expenseList = arrayListOf<ExpenseModel>()

    private val ALL_CARD = 1
    private val LAST_CARD = 2

    private lateinit var navigator: Navigator

    inner class DashboardViewHolder(val binding: ItemDashboardBinding): RecyclerView.ViewHolder(binding.root)

    inner class LastCardViewHolder(val binding: ItemMoreBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LAST_CARD) {
            return LastCardViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_more,
                    parent,
                    false
                )
            )
        }
        return DashboardViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_dashboard,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (expenseList.size > LIMIT_ITEM) {
            LIMIT_ITEM + 1
        } else {
            expenseList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == LIMIT_ITEM) {
            LAST_CARD
        } else {
            ALL_CARD
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == LIMIT_ITEM) {
            (holder as LastCardViewHolder).binding.btnMore.setOnClickListener {
                navigator.onMoreClick(type)
            }
        } else {
            (holder as DashboardViewHolder).binding.expense = expenseList[position]
            holder.itemView.setOnClickListener {
                navigator.onDialogClick(expenseList[position], type)
            }
        }
    }

    fun submitList(newExpenseList: List<ExpenseModel>) {
        expenseList.clear()
        expenseList.addAll(newExpenseList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        expenseList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, expenseList.size)
    }

    fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
    }
}