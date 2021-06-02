package com.logo.data.repository

import com.logo.data.api.ApiClient
import com.logo.data.api.ApiService
import com.logo.data.model.headline.TopHeadline
import retrofit2.Call


interface IHeadlineRepository {
    fun getTopHeadlines() : Call<TopHeadline>
}

class HeadlineRepository : IHeadlineRepository {

    private val apiService: ApiService by lazy {
        ApiClient.retrofit.create(ApiService::class.java)
    }

    override fun getTopHeadlines(): Call<TopHeadline> {
        return apiService.getTopLines()
    }
}