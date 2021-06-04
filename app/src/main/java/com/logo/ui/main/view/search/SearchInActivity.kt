package com.logo.ui.main.view.search

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

class SearchInActivity : BaseActivity<ActivitySearchInBinding>() {
    override var layoutResource = R.layout.activity_search_in

    private val preference by lazy { PreferencesManager.instantiate(this) }

    private var searchPlace: SearchPlaceList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesManager.instantiate(this)
        initListener()
    }

    override fun onResume() {
        super.onResume()
        searchPlace = Gson().fromJson(
            preference.get(SharePreferenceKey.SEARCH_IN),
            SearchPlaceList::class.java
        )
        initView()
    }

    private fun initView() {
        searchPlace?.let {
            binding.switchTitle.isChecked = it.list[0].isSelected
            binding.switchDescription.isChecked = it.list[1].isSelected
            binding.switchContent.isChecked = it.list[2].isSelected
        }
        binding.buttonApply.isEnabled = false
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
                binding.buttonApply.isEnabled = it.list[0].isSelected != isChecked
            }

        }
        binding.switchDescription.setOnCheckedChangeListener { _, isChecked ->
            searchPlace?.let {
                binding.buttonApply.isEnabled = it.list[1].isSelected != isChecked
            }
        }
        binding.switchContent.setOnCheckedChangeListener { _, isChecked ->
            searchPlace?.let {
                binding.buttonApply.isEnabled = it.list[2].isSelected != isChecked
            }
        }
    }

    private fun clearChoices() {
        binding.switchTitle.isChecked = false
        binding.switchDescription.isChecked = false
        binding.switchContent.isChecked = false
        storeChoices()
        binding.buttonApply.isEnabled = false
    }

    private fun storeChoices() {
        preference.store(
            SharePreferenceKey.SEARCH_IN,
            SearchPlaceList(
                arrayListOf(
                    SearchPlace(
                        Constants.TITLE, binding.switchTitle.isChecked
                    ),
                    SearchPlace(
                        Constants.DESCRIPTION, binding.switchDescription.isChecked
                    ),
                    SearchPlace(
                        Constants.CONTENT, binding.switchContent.isChecked
                    )
                )
            )
        )
    }
}