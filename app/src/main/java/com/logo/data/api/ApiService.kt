package com.logo.data.api

import com.logo.data.ApiConstants
import com.logo.data.model.headline.TopHeadline
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(ApiConstants.TOP_HEADLINES)
    fun getTopLines(
        @Query(ApiConstants.API_TOKEN_QUERY) token: String = ApiConstants.API_TOKEN,
        @Query(ApiConstants.MAX) max: Int = 10,
    ): Call<TopHeadline>


    @GET(ApiConstants.SEARCH)
    fun search(
        @Query(ApiConstants.SEARCH_QUERY) searchQuery: String,
        @Query(ApiConstants.SEARCH_PLACE) searchPlace: String,
        @Query(ApiConstants.SEARCH_DATE_FROM) searchDateFrom: String,
        @Query(ApiConstants.SEARCH_DATE_TO) searchDateTo: String,
        @Query(ApiConstants.SORT_BY) sortBy: String,
        @Query(ApiConstants.MAX) max: Int = 10,
        @Query(ApiConstants.API_TOKEN_QUERY) token: String = ApiConstants.API_TOKEN
    ): Call<TopHeadline>

}