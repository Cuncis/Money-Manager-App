package com.cuncisboss.moneymanagerapp.ui

import com.cuncisboss.moneymanagerapp.model.ExpenseModel

interface Navigator {
    fun dialogClick()
    fun onMoreClick(type: String)
    fun onDialogClick(expense: ExpenseModel)
}