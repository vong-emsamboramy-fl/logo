package com.logo.ui.main.view.master

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.logo.R
import com.logo.data.model.headline.SearchPlaceList
import com.logo.data.model.search.SortQuery
import com.logo.databinding.FragmentSearchBinding
import com.logo.ui.base.BaseFragment
import com.logo.ui.main.view.search.FilterActivity
import com.logo.ui.main.view.search.SortBottomSheet
import com.logo.ui.main.view.search.SortBottomSheetListener
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutResource = R.layout.fragment_search

    private val preference by lazy {
        PreferencesManager.instantiate(requireContext())
    }

    private val bottomSheetListener = object : SortBottomSheetListener {
        override fun onBottomSheetDismissed(sortQuery: SortQuery) {
            binding.imageViewSort.isActivated = false
            preference.store(SharePreferenceKey.SORT_QUERY, Gson().toJson(sortQuery))
        }
    }
    private val bottomSheetSort = SortBottomSheet(bottomSheetListener)

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
            startActivity(Intent(requireContext(), FilterActivity::class.java))
        }
        binding.imageViewSort.setOnClickListener {
            binding.imageViewSort.isActivated = true
            val sortQuery = Gson().fromJson(
                preference.get(SharePreferenceKey.SORT_QUERY),
                SortQuery::class.java
            )
            if (sortQuery == null) {
                bottomSheetSort.setQuery(SortQuery.UPLOADED_DATE)
            } else {
                bottomSheetSort.setQuery(sortQuery)
            }
            bottomSheetSort.show(childFragmentManager, "sort")
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