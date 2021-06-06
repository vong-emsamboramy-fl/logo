package com.logo.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.logo.R
import com.logo.databinding.ActivityNewsDetailBinding
import com.logo.ui.base.BaseActivity
import com.logo.utils.constants.IntentKey


class NewsDetailActivity : BaseActivity<ActivityNewsDetailBinding>() {
    override var layoutResource = R.layout.activity_news_detail

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgress()
        getIntentData()
        initView()
        initListener()
    }

    private fun getIntentData() {
        url = intent.getStringExtra(IntentKey.WEB_URL)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        url?.let {
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(it)
            binding.webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return false
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    dismissProgress()
                }
            }
        }
    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}