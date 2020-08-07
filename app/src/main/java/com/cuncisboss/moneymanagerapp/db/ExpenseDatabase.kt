package com.cuncisboss.moneymanagerapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cuncisboss.moneymanagerapp.model.ExpenseModel

@Database(entities = [ExpenseModel::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
}