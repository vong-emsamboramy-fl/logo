package com.logo.ui.main.view.master

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.logo.R
import com.logo.data.model.headline.Article
import com.logo.data.model.headline.ArticleSource
import com.logo.data.model.headline.SearchPlaceList
import com.logo.data.model.search.SearchModel
import com.logo.data.model.search.SortQuery
import com.logo.databinding.FragmentSearchBinding
import com.logo.ui.base.BaseFragment
import com.logo.ui.main.adapter.HeadlineAdapter
import com.logo.ui.main.view.search.FilterActivity
import com.logo.ui.main.view.search.SortBottomSheet
import com.logo.ui.main.view.search.SortBottomSheetListener
import com.logo.ui.main.viewModel.TopHeadlineViewModel
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey
import com.logo.utils.Status
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutResource = R.layout.fragment_search

    private val viewModel: TopHeadlineViewModel by lazy {
        ViewModelProvider(this).get(TopHeadlineViewModel::class.java)
    }

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

    private val adapter = HeadlineAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initListener()
        initView()
    }

    private fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        setUpFilterAmount()
    }

    private fun observeViewModel() {
        viewModel.observeSearch.observe(requireActivity()) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    dismissProgress()
                    it.data?.let { mainData ->
                        // header data
                        adapter.set(
                            Article(
                                "${mainData.totalArticles} ${getString(R.string.news)}",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ArticleSource("", "")
                            )
                        )
                        adapter.add(mainData.articles)
                    }
                }
                Status.ERROR -> {
                    dismissProgress()
                    showErrorDialog()
                }
            }
        }
    }

    private fun initListener() {
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        val gson = Gson()
                        val searchPlaceList = gson.fromJson(
                            preference.get(SharePreferenceKey.SEARCH_IN),
                            SearchPlaceList::class.java
                        )
                        val dateFrom = gson.fromJson(
                            preference.get(SharePreferenceKey.FILTER_FROM_DATE),
                            Date::class.java
                        )
                        val dateTo = gson.fromJson(
                            preference.get(SharePreferenceKey.FILTER_TO_DATE),
                            Date::class.java
                        )
                        val sortBy = gson.fromJson(
                            preference.get(SharePreferenceKey.SORT_QUERY),
                            SortQuery::class.java
                        )
                        sortBy?.let {
                            viewModel.search(
                                SearchModel(
                                    query,
                                    searchPlaceList,
                                    dateFrom,
                                    dateTo,
                                    it
                                )
                            )
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }
            }
        )

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