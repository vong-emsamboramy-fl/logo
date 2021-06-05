package com.logo.ui.main.view.search

import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.logo.R
import com.logo.data.model.headline.SearchPlace
import com.logo.data.model.headline.SearchPlaceList
import com.logo.databinding.ActivitySearchInBinding
import com.logo.ui.base.BaseActivity
import com.logo.utils.PreferencesManager
import com.logo.utils.SharePreferenceKey
import com.logo.utils.constants.Constants
import com.logo.utils.constants.IntentKey

class SearchInActivity : BaseActivity<ActivitySearchInBinding>() {
    override var layoutResource = R.layout.activity_search_in

    private var searchPlace: SearchPlaceList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
    }

    override fun onResume() {
        super.onResume()
        setSearchPlace()
    }

    private fun setSearchPlace() {
        searchPlace = Gson().fromJson(
            intent.getStringExtra(IntentKey.SEARCH_PLACE),
            SearchPlaceList::class.java
        )
        if (searchPlace == null) {
            searchPlace = SearchPlaceList(
                arrayListOf(
                    SearchPlace("title", false),
                    SearchPlace("description", false),
                    SearchPlace("content", false)
                )
            )
        }
        searchPlace?.let {
            binding.switchTitle.isChecked = it.list[0].isSelected
            binding.switchDescription.isChecked = it.list[1].isSelected
            binding.switchContent.isChecked = it.list[2].isSelected
        }
    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.layoutClear.setOnClickListener {
            clearChoices()
        }
        binding.buttonApply.setOnClickListener {
            storeChoices()
            onBackPressed()
        }
        binding.switchTitle.setOnCheckedChangeListener { _, isChecked ->
            searchPlace?.let {
                it.list[0].isSelected = isChecked
            }
        }
        binding.switchDescription.setOnCheckedChangeListener { _, isChecked ->
            searchPlace?.let {
                it.list[1].isSelected = isChecked
            }
        }
        binding.switchContent.setOnCheckedChangeListener { _, isChecked ->
            searchPlace?.let {
                it.list[2].isSelected = isChecked
            }
        }
    }

    private fun clearChoices() {
        binding.switchTitle.isChecked = false
        binding.switchDescription.isChecked = false
        binding.switchContent.isChecked = false
    }

    private fun storeChoices() {
        setResult(RESULT_OK, Intent().apply {
            putExtra(IntentKey.SEARCH_PLACE, Gson().toJson(searchPlace))
        })
        finish()
//        if (!binding.switchTitle.isChecked && !binding.switchDescription.isChecked && !binding.switchContent.isChecked) {
//            preference.remove(SharePreferenceKey.SEARCH_IN)
//        } else {
//            preference.store(
//                SharePreferenceKey.SEARCH_IN,
//                SearchPlaceList(
//                    arrayListOf(
//                        SearchPlace(
//                            Constants.TITLE, binding.switchTitle.isChecked
//                        ),
//                        SearchPlace(
//                            Constants.DESCRIPTION, binding.switchDescription.isChecked
//                        ),
//                        SearchPlace(
//                            Constants.CONTENT, binding.switchContent.isChecked
//                        )
//                    )
//                )
//            )
//        }
    }
}