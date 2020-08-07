package com.cuncisboss.moneymanagerapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cuncisboss.moneymanagerapp.model.ExpenseModel
import com.cuncisboss.moneymanagerapp.util.Constants.EXPENSE_TEXT
import com.cuncisboss.moneymanagerapp.util.Constants.INCOME_TEXT

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: ExpenseModel)

    @Delete
    suspend fun delete(expense: ExpenseModel)

    @Update
    suspend fun update(expense: ExpenseModel)

    @Query("SELECT * FROM expense_table WHERE type ='${INCOME_TEXT}'")
    fun getAllIncome(): LiveData<List<ExpenseModel>>

    @Query("SELECT * FROM expense_table WHERE type ='${EXPENSE_TEXT}'")
    fun getAllExpense(): LiveData<List<ExpenseModel>>

}