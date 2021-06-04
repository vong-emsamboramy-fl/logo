package com.logo.ui.main.view.search

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doOnTextChanged
import com.logo.R
import com.logo.databinding.ActivityFilterBinding
import com.logo.ui.base.BaseActivity
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class FilterActivity : BaseActivity<ActivityFilterBinding>() {

    override var layoutResource = R.layout.activity_filter

    private val datePattern = "yyyy/MM/dd"
    private val serverFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private val serverFormatter by lazy {
        SimpleDateFormat(serverFormat)
    }
    private val dateFormatter by lazy {
        SimpleDateFormat(datePattern)
    }

    private var selectedFromDate: Date? = null
    private var selectedToDate: Date? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.editTextFrom.setOnClickListener {
            openDateFromPicker()
        }

        binding.editTextTo.setOnClickListener {
            openDateToPicker()
        }
    }

    private fun openDateFromPicker() {
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                binding.editTextFrom.setText(dateFormatter.format(calendar.time))
                selectedFromDate = calendar.time
            },
            Calendar.YEAR,
            Calendar.MONTH,
            Calendar.DAY_OF_MONTH
        ).apply {
            datePicker.minDate = System.currentTimeMillis() - 1000
            selectedToDate?.let {
                datePicker.maxDate = it.time - 1000
            }

        }.show()
    }

    private fun openDateToPicker() {
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                binding.editTextTo.setText(dateFormatter.format(calendar.time))
                selectedToDate = calendar.time
            },
            Calendar.YEAR,
            Calendar.MONTH,
            Calendar.DAY_OF_MONTH
        ).apply {
            if (selectedFromDate != null) {
                selectedFromDate?.let {
                    datePicker.minDate = it.time - 1000
                }
            } else {
                datePicker.minDate = System.currentTimeMillis() - 1000
            }
        }.show()
    }
}