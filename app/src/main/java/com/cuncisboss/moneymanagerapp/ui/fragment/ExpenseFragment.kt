package com.cuncisboss.moneymanagerapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.cuncisboss.moneymanagerapp.R
import com.cuncisboss.moneymanagerapp.databinding.FragmentExpenseBinding
import com.cuncisboss.moneymanagerapp.model.ExpenseModel
import com.cuncisboss.moneymanagerapp.util.Constants.reverseThis
import com.cuncisboss.moneymanagerapp.util.TextHelper
import com.cuncisboss.moneymanagerapp.viewmodel.ExpenseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_insert_update.view.*
import org.koin.android.ext.android.inject


class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding

    private val viewModel by inject<ExpenseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.expenseTotal.observe(viewLifecycleOwner, Observer {
            binding.totalExpense = TextHelper.longToString(it.toLong())
        })

        viewModel.getAllExpense().observe(viewLifecycleOwner, Observer {
            viewModel.setExpenseTotal(it)
            it.reverseThis()
            viewModel.adapter.submitList(it)
        })

        viewModel.adapter.setListener { expense ->
            dialogAlert(expense)
        }
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
                Snackbar.make(requireView(), "Deleted", Snackbar.LENGTH_LONG).show()
            }
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun dialogInsertUpdate(expense: ExpenseModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_insert_update, null as ViewGroup?)
        builder.setView(view)

        val type = expense.type
        val dialog = builder.create()

        if (expense.type.equals("income", true)) {
            view.sp_type.setSelection(0)
        } else {
            view.sp_type.setSelection(1)
        }
        view.sp_type.isEnabled = false
        view.et_amount.setText(expense.nominal.toString())
        view.et_note.setText(expense.note)
        view.btnSave.text = getString(R.string.update)

        view.btnCancel.setOnClickListener {
            dialog.cancel()
        }
        view.btnSave.setOnClickListener {
            when {
                view.et_amount.text.toString().isEmpty() -> {
                    view.et_amount.error = "Amount field cannot be empty"
                }
                view.et_note.text.toString().isEmpty() -> {
                    view.et_note.error = "Note field cannot be empty"
                }
                else -> {
                    val amount = view.et_amount.text.toString().toLong()
                    val note = view.et_note.text.toString()

                    viewModel.update(ExpenseModel(note, type, amount, expense.datetime, expense.id))
                    Snackbar.make(requireView(), "$type updated", Snackbar.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

}