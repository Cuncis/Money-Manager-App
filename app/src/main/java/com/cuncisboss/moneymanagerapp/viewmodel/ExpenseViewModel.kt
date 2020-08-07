package com.cuncisboss.moneymanagerapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncisboss.moneymanagerapp.adapter.DashboardAdapter
import com.cuncisboss.moneymanagerapp.adapter.ExpenseAdapter
import com.cuncisboss.moneymanagerapp.db.ExpenseDao
import com.cuncisboss.moneymanagerapp.model.ExpenseModel
import com.cuncisboss.moneymanagerapp.ui.Navigator
import com.cuncisboss.moneymanagerapp.util.Constants.TAG
import kotlinx.coroutines.launch

class ExpenseViewModel(private val expenseDao: ExpenseDao) : ViewModel() {

    val incomeAdapter = DashboardAdapter("Income")
    val expenseAdapter = DashboardAdapter("Expense")

    val adapter = ExpenseAdapter()

    var incomeTotal = MutableLiveData<String>()
    var expenseTotal = MutableLiveData<String>()

    private lateinit var navigator: Navigator


    fun setIncomeTotal(list: List<ExpenseModel>) {
        var total = 0.0
        for (i in list.indices) {
            total += list[i].nominal
        }
        incomeTotal.postValue(total.toInt().toString())
    }

    fun setExpenseTotal(list: List<ExpenseModel>) {
        var total = 0.0
        for (i in list.indices) {
            total += list[i].nominal
        }
        expenseTotal.postValue(total.toInt().toString())
    }

    fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
    }

    fun insert(expense: ExpenseModel) = viewModelScope.launch {
        expenseDao.insert(expense)
    }

    fun getAllIncome(): LiveData<List<ExpenseModel>> {
        return expenseDao.getAllIncome()
    }

    fun getAllExpense(): LiveData<List<ExpenseModel>> {
        return expenseDao.getAllExpense()
    }

    fun update(expense: ExpenseModel) = viewModelScope.launch {
        expenseDao.update(expense)
    }

    fun delete(expense: ExpenseModel) = viewModelScope.launch {
        expenseDao.delete(expense)
    }

    fun clickListener() {
        navigator.dialogClick()
    }
}