package com.logo.ui.main.view.master

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson
import com.logo.R
import com.logo.data.model.headline.SearchPlaceList
import com.logo.databinding.FragmentSearchBinding
import com.logo.ui.base.BaseFragment
import com.logo.ui.main.view.search.FilterActivity
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey
import com.logo.utils.constants.IntentKey
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutResource = R.layout.fragment_search

    private val preference by lazy {
        PreferencesManager.instantiate(requireContext())
    }

    private val filterResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
//                result.data?.let {
//                    it.getStringExtra(IntentKey.SEARCH_PLACE)
//                }
            }
        }

    private val sortResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    override fun onResume() {
        super.onResume()
        setUpFilterAmount()
    }

    private fun initListener() {
        binding.imageViewFilter.setOnClickListener {
            filterResult.launch(Intent(requireContext(), FilterActivity::class.java))
        }
    }

    private fun setUpFilterAmount() {
        var filterAmount = 0
        preference.get(SharePreferenceKey.SEARCH_IN, SearchPlaceList::class.java)?.let {
            filterAmount += 1
        }
        preference.get(SharePreferenceKey.FILTER_FROM_DATE, Date::class.java)?.let {
            filterAmount += 1
        }
        preference.get(SharePreferenceKey.FILTER_TO_DATE, Date::class.java)?.let {
            filterAmount += 1
        }
        if (filterAmount != 0) {
            binding.textViewFilterAmount.text = filterAmount.toString()
            binding.textViewFilterAmount.visibility = View.VISIBLE
        } else {
            binding.textViewFilterAmount.visibility = View.GONE
        }
    }
}