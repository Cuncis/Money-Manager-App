package com.cuncisboss.moneymanagerapp.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.cuncisboss.moneymanagerapp.db.ExpenseDatabase
import com.cuncisboss.moneymanagerapp.util.Constants.EXPENSE_DB_NAME
import com.cuncisboss.moneymanagerapp.util.Constants.PREF_NAME
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

    single { providePref(androidApplication()) }

    viewModel { ExpenseViewModel(get(), get()) }

}

fun providePref(app: Application): SharedPreferences =
    app.getSharedPreferences(PREF_NAME, MODE_PRIVATE)