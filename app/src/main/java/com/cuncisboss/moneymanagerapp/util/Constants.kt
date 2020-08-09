package com.cuncisboss.moneymanagerapp.util

import com.cuncisboss.moneymanagerapp.model.ExpenseModel

object Constants {
    const val TAG = "_logMoneyManager"
    const val EXPENSE_DB_NAME = "expense_db"

    const val INCOME_TEXT = "Income"
    const val EXPENSE_TEXT = "Expense"

    const val LIMIT_ITEM = 5

    const val PREF_NAME = "com.cuncisboss.moneymanagerapp.PREF_NAME"
    const val KEY_CURRENCY = "NONE"

    fun List<ExpenseModel>.reverseThis() {
        (this as ArrayList).reverse()
    }

}