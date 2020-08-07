package com.cuncisboss.moneymanagerapp.util

import com.cuncisboss.moneymanagerapp.model.ExpenseModel

object Constants {
    const val TAG = "_logMoneyManager"
    const val EXPENSE_DB_NAME = "expense_db"

    const val INCOME_TEXT = "Income"
    const val EXPENSE_TEXT = "Expense"

    fun List<ExpenseModel>.reverseThis() {
        (this as ArrayList).reverse()
    }

}