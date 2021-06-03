package com.logo.ui.main.view.master

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.logo.R
import com.logo.databinding.FragmentSearchBinding
import com.logo.ui.base.BaseFragment
import com.logo.ui.main.view.search.FilterActivity
import com.logo.ui.main.view.search.SearchInActivity

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutResource = R.layout.fragment_search

    private val filterResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
        }
    }

    private val sortResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.imageViewFilter.setOnClickListener {
            filterResult.launch(Intent(requireContext(), FilterActivity::class.java))
        }
        binding.imageViewSort.setOnClickListener {
            sortResult.launch(Intent(requireContext(), SearchInActivity::class.java))
        }
    }
}