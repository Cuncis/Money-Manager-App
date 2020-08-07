package com.cuncisboss.moneymanagerapp.di

import androidx.room.Room
import com.cuncisboss.moneymanagerapp.db.ExpenseDatabase
import com.cuncisboss.moneymanagerapp.util.Constants.EXPENSE_DB_NAME
import com.cuncisboss.moneymanagerapp.viewmodel.ExpenseViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { Room.databaseBuilder(
        androidApplication(),
        ExpenseDatabase::class.java,
        EXPENSE_DB_NAME
    ).build().expenseDao() }

    viewModel { ExpenseViewModel(get()) }
}