package com.cuncisboss.moneymanagerapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cuncisboss.moneymanagerapp.R
import com.cuncisboss.moneymanagerapp.databinding.FragmentDashboardBinding
import com.cuncisboss.moneymanagerapp.model.ExpenseModel
import com.cuncisboss.moneymanagerapp.ui.Navigator
import com.cuncisboss.moneymanagerapp.util.Constants.reverseThis
import com.cuncisboss.moneymanagerapp.util.TextHelper
import com.cuncisboss.moneymanagerapp.viewmodel.ExpenseViewModel
import kotlinx.android.synthetic.main.dialog_insert_update.view.*
import org.koin.android.ext.android.inject


class DashboardFragment : Fragment(), Navigator {

    private lateinit var binding: FragmentDashboardBinding

    private val viewModel by inject<ExpenseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        viewModel.setNavigator(this)
        viewModel.incomeAdapter.setNavigator(this)
        viewModel.expenseAdapter.setNavigator(this)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.incomeTotal.observe(viewLifecycleOwner, Observer {
            binding.totalIncome = TextHelper.longToString(it.toLong())
        })

        viewModel.expenseTotal.observe(viewLifecycleOwner, Observer {
            binding.totalExpense = TextHelper.longToString(it.toLong())
        })

        viewModel.getAllIncome().observe(viewLifecycleOwner, Observer {
            binding.incomeSize = it.size
            viewModel.setIncomeTotal(it)
            it.reverseThis()
            viewModel.incomeAdapter.submitList(it)
        })

        viewModel.getAllExpense().observe(viewLifecycleOwner, Observer {
            binding.expenseSize = it.size
            viewModel.setExpenseTotal(it)
            it.reverseThis()
            viewModel.expenseAdapter.submitList(it)
        })
    }

    private fun dialogInsertUpdate(expense: ExpenseModel = ExpenseModel()) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_insert_update, null as ViewGroup?)
        builder.setView(view)

        var type = ""
        val dialog = builder.create()

        if (expense.note.isNotEmpty()) {
            type = expense.type
            if (expense.type.equals("income", true)) {
                view.sp_type.setSelection(0)
            } else {
                view.sp_type.setSelection(1)
            }
            view.et_amount.setText(expense.nominal.toString())
            view.et_note.setText(expense.note)
            view.btnSave.text = getString(R.string.update)
        }

        view.sp_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                v: View?,
                position: Int,
                id: Long
            ) {
                type = view.sp_type.selectedItem.toString()
            }
        }

        view.btnCancel.setOnClickListener {
            dialog.cancel()
        }
        view.btnSave.setOnClickListener {
            val amount = view.et_amount.text.toString().toLong()
            val note = view.et_note.text.toString()

            if (expense.note.isNotEmpty()) {
                viewModel.update(ExpenseModel(note, type, amount, expense.datetime, expense.id))
            } else {
                viewModel.insert(ExpenseModel(
                    note,
                    type,
                    amount
                ))
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun dialogClick() {
        dialogInsertUpdate()
    }

    private fun dialogAlert(expense: ExpenseModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(true)

        val list = arrayOf("Update", "Delete")
        builder.setItems(list) { dialog, position ->
            if (list[position].equals("update", true)) {
                dialogInsertUpdate(expense)
            } else {
                viewModel.delete(expense)
            }
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onMoreClick(type: String) {
        if (type.equals("income", true)) {
            findNavController().navigate(R.id.action_dashboardFragment_to_incomeFragment)
        } else if (type.equals("expense", true)){
            findNavController().navigate(R.id.action_dashboardFragment_to_expenseFragment)
        }
    }

    override fun onDialogClick(expense: ExpenseModel) {
        dialogAlert(expense)
    }
}












