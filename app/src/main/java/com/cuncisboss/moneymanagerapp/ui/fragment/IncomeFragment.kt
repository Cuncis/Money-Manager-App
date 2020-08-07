package com.cuncisboss.moneymanagerapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.cuncisboss.moneymanagerapp.R
import com.cuncisboss.moneymanagerapp.databinding.FragmentIncomeBinding
import com.cuncisboss.moneymanagerapp.util.Constants.reverseThis
import com.cuncisboss.moneymanagerapp.viewmodel.ExpenseViewModel
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject


class IncomeFragment : Fragment() {

    private lateinit var binding: FragmentIncomeBinding

    private val viewModel by inject<ExpenseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.incomeTotal.observe(viewLifecycleOwner, Observer {
            binding.totalIncome = it
        })

        viewModel.getAllIncome().observe(viewLifecycleOwner, Observer {
            viewModel.setIncomeTotal(it)
            it.reverseThis()
            viewModel.adapter.submitList(it)
        })
    }

}