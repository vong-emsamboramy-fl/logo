package com.logo.ui.main.view.search

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.logo.R
import com.logo.data.model.headline.SearchPlaceList
import com.logo.databinding.ActivityFilterBinding
import com.logo.ui.base.BaseActivity
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey
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

    private val preference by lazy {
        PreferencesManager.instantiate(this)
    }

    private var selectedFromDate: Date? = null
    private var selectedToDate: Date? = null

    private var searchPlace: SearchPlaceList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
    }

    override fun onResume() {
        super.onResume()
        searchPlace = Gson().fromJson(
            preference.get(SharePreferenceKey.SEARCH_IN),
            SearchPlaceList::class.java
        )
        when {
            searchPlace?.list?.all { it.isSelected } == true -> {
                binding.textViewSearchStatus.text = getString(R.string.all)
            }
            searchPlace?.list?.none { it.isSelected } == true -> {
                binding.textViewSearchStatus.text = getString(R.string.none)
            }
            else -> {
                val choiceList = searchPlace?.list?.filter { it.isSelected }
                choiceList?.let {
                    binding.textViewSearchStatus.text = it.fold("", { acc, next ->
                        if (acc.isEmpty()) {
                            "$acc ${next.tile}"
                        } else {
                            "$acc, ${next.tile}"
                        }
                    })
                }
            }
        }
    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.editTextFrom.setOnClickListener {
            openDateFromPicker()
        }

        binding.editTextTo.setOnClickListener {
            openDateToPicker()
        }
        binding.layoutSearchIn.setOnClickListener {
            startActivity(Intent(this, SearchInActivity::class.java))
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