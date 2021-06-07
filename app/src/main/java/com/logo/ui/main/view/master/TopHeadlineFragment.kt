package com.logo.ui.main.view.master

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.logo.R
import com.logo.data.model.headline.Article
import com.logo.data.model.headline.ArticleSource
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
                    binding.textViewNews.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()
                }
                Status.SUCCESS -> {
                    binding.textViewNews.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.GONE
                    binding.shimmerLayout.stopShimmer()
                    it.data?.let { mainData ->
                        // header data
                        adapter.add(
                            Article(
                                getString(R.string.news),
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
                    binding.textViewNews.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.GONE
                    binding.shimmerLayout.stopShimmer()
                    showErrorDialog()
                }
            }
        }
    }
}