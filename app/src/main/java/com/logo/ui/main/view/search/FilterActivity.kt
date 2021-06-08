package com.logo.ui.main.view.search

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson
import com.logo.R
import com.logo.data.model.headline.SearchPlaceList
import com.logo.databinding.ActivityFilterBinding
import com.logo.ui.base.BaseActivity
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey
import com.logo.utils.constants.IntentKey
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class FilterActivity : BaseActivity<ActivityFilterBinding>() {

    override var layoutResource = R.layout.activity_filter

    private val datePattern = "yyyy/MM/dd"
    private val dateFormatter by lazy {
        SimpleDateFormat(datePattern)
    }

    private val preference by lazy {
        PreferencesManager.instantiate(this)
    }

    private val searchPlaceResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    searchPlace = Gson().fromJson(
                        it.getStringExtra(IntentKey.SEARCH_PLACE),
                        SearchPlaceList::class.java
                    )
                }
            }
        }
    private var selectedFromDate: Date? = null
    private var selectedToDate: Date? = null

    private var searchPlace: SearchPlaceList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initListener()
        setFilterFromDate()
        setFilterToDate()
    }

    override fun onResume() {
        super.onResume()
        setUpData()
    }

    private fun initData() {
        searchPlace = Gson().fromJson(
            preference.get(SharePreferenceKey.SEARCH_IN),
            SearchPlaceList::class.java
        )
    }

    private fun setUpData() {
        setSearchPlace()
    }

    private fun setSearchPlace() {
        if (searchPlace == null) {
            binding.textViewSearchStatus.text = getString(R.string.none)
        }
        searchPlace?.let { searchList ->
            when {
                searchList.list.all { it.isSelected } -> {
                    binding.textViewSearchStatus.text = getString(R.string.all)
                }
                searchList.list.none { it.isSelected } -> {
                    binding.textViewSearchStatus.text = getString(R.string.none)
                }
                else -> {
                    val choiceList = searchList.list.filter { it.isSelected }
                    choiceList.let {
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
    }

    private fun setFilterFromDate() {
        selectedFromDate = preference.get(SharePreferenceKey.FILTER_FROM_DATE, Date::class.java)
        selectedFromDate?.let {
            binding.editTextFrom.setText(dateFormatter.format(it))
        }
    }

    private fun setFilterToDate() {
        selectedToDate = preference.get(SharePreferenceKey.FILTER_TO_DATE, Date::class.java)
        selectedToDate?.let {
            binding.editTextTo.setText(dateFormatter.format(it))
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
            searchPlaceResult.launch(Intent(this, SearchInActivity::class.java).apply {
                putExtra(IntentKey.SEARCH_PLACE, Gson().toJson(searchPlace))
            })
        }
        binding.buttonApply.setOnClickListener {
            preference.store(SharePreferenceKey.FILTER_FROM_DATE, Gson().toJson(selectedFromDate))
            preference.store(SharePreferenceKey.FILTER_TO_DATE, Gson().toJson(selectedToDate))
            preference.store(SharePreferenceKey.SEARCH_IN, Gson().toJson(searchPlace))
            onBackPressed()
        }
        binding.layoutClear.setOnClickListener {
            selectedFromDate = null
            selectedToDate = null
            searchPlace = null
            binding.editTextFrom.setText("")
            binding.editTextTo.setText("")
            binding.textViewSearchStatus.text = getString(R.string.none)
            clearStoredData()
        }
    }

    private fun clearStoredData() {
        preference.remove(SharePreferenceKey.FILTER_FROM_DATE)
        preference.remove(SharePreferenceKey.FILTER_TO_DATE)
        preference.remove(SharePreferenceKey.SEARCH_IN)
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