package com.cuncisboss.moneymanagerapp.util

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getCurrentDatetime(): String {
        return SimpleDateFormat("dd MMM yyyy", Locale.US).format(Date())
    }
}