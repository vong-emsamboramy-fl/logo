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

}