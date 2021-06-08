package com.logo.ui.main.view.master

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.logo.R
import com.logo.data.model.headline.Article
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

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.deleteAllArticles()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observeViewModel()
        initShimmer()
    }

    private fun initView() {
        binding.recyclerView.adapter = adapter
    }

    private fun initListener() {
        binding.refreshLayout.setOnRefreshListener(refreshListener)
    }

    private fun observeViewModel() {
        viewModel.observeTopHeadlines.observe(requireActivity()) {
            binding.refreshLayout.isRefreshing = false
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { mainData ->
                        cacheArticles(mainData.articles)
                    }
                }
                Status.ERROR -> {
                    binding.textViewNews.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.GONE
                    binding.shimmerLayout.stopShimmer()
                    showErrorDialog()
                }
                else -> {
                }
            }
        }
        viewModel.articleList.observe(requireActivity()) {
            if (it.isEmpty()) {
                viewModel.getTopHeadlines()
            } else {
                setItems(it)
            }
        }
    }

    private fun initShimmer() {
        binding.textViewNews.visibility = View.VISIBLE
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmer()
    }

    private fun setItems(articleList: List<Article>) {
        binding.textViewNews.visibility = View.GONE
        binding.shimmerLayout.visibility = View.GONE
        binding.shimmerLayout.stopShimmer()
        articleList.let { mainData ->
            adapter.clear()
            // header data
            adapter.add(
                Article(
                    0,
                    getString(R.string.news),
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
            adapter.add(mainData)
        }
    }

    private fun cacheArticles(list: List<Article>) {
        viewModel.addArticle(list)
    }
}