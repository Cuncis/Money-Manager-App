package com.cuncisboss.moneymanagerapp.util

object TextHelper {

    fun longToString(nominal: Long): String {
        return String.format("%,d", nominal).replace(',', '.')
    }
}