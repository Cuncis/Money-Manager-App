package com.cuncisboss.moneymanagerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cuncisboss.moneymanagerapp.util.DateHelper

@Entity(tableName = "expense_table")
data class ExpenseModel(
    val note: String = "",
    val type: String = "",
    val nominal: Long = 0L,
    val datetime: String = DateHelper.getCurrentDatetime(),
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)