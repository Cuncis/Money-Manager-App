package com.cuncisboss.moneymanagerapp.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.cuncisboss.moneymanagerapp.R
import com.cuncisboss.moneymanagerapp.adapter.DashboardAdapter
import com.google.android.material.textview.MaterialTextView



@BindingAdapter("currency", "separate_text")
fun setSeparateTextView(textView: MaterialTextView, currency: String, nominal: String) {
    textView.text = String.format("%s %s", currency, String.format("%,d", nominal.toLong()).replace(',', '.'))
}

@BindingAdapter("visibility_list")
fun setVisibilityList(textView: MaterialTextView, adapter: DashboardAdapter) {
    if (adapter.itemCount != 0) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
    }
}