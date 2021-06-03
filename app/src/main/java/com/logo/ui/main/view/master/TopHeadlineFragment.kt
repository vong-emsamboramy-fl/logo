package com.logo.ui.main.view.master

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.logo.R
import com.logo.databinding.FragmentTopHeadlinesBinding
import com.logo.ui.base.BaseFragment
import com.logo.ui.main.adapter.HeadlineAdapter
import com.logo.ui.main.viewModel.TopHeadlineViewModel
import com.logo.utils.Status

class TopHeadlineFragment : BaseFragment<FragmentTopHeadlinesBinding>() {
    override val layoutResource = R.layout.fragment_top_headlines

    private val adapter = HeadlineAdapter()

    private val viewModel: TopHeadlineViewModel by lazy {
        ViewModelProvider(this).get(TopHeadlineViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        viewModel.getTopHeadlines()
    }

    private fun initView() {
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.observeTopHeadlines.observe(requireActivity()) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    dismissProgress()
                    it.data?.let { mainData ->
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
}