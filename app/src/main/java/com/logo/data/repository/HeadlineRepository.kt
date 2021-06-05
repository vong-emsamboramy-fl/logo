package com.logo.data.repository

import com.logo.data.api.ApiClient
import com.logo.data.api.ApiService
import com.logo.data.model.headline.TopHeadline
import com.logo.data.model.search.SearchModel
import retrofit2.Call


interface IHeadlineRepository {
    fun getTopHeadlines(): Call<TopHeadline>
    fun search(body: SearchModel): Call<TopHeadline>
}

class HeadlineRepository : IHeadlineRepository {

    private val apiService: ApiService by lazy {
        ApiClient.retrofit.create(ApiService::class.java)
    }

    override fun getTopHeadlines(): Call<TopHeadline> {
        return apiService.getTopLines()
    }

    override fun search(body: SearchModel): Call<TopHeadline> {
        return apiService.search(
            body.query,
            body.placeText,
            body.dateFromText,
            body.dateToText,
            body.sortBy.text
        )
    }
}