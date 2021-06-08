package com.logo.data.repository

import androidx.lifecycle.LiveData
import com.logo.data.api.ApiClient
import com.logo.data.api.ApiService
import com.logo.data.model.headline.Article
import com.logo.data.model.headline.ArticleDao
import com.logo.data.model.headline.TopHeadline
import com.logo.data.model.search.SearchHistory
import com.logo.data.model.search.SearchModel
import retrofit2.Call


interface IHeadlineRepository {
    fun getTopHeadlines(): Call<TopHeadline>
    fun search(body: SearchModel): Call<TopHeadline>
    suspend fun addArticleList(articleList: List<Article>)
    suspend fun deleteAllArticles()
}

class HeadlineRepository(private val articleDao: ArticleDao) : IHeadlineRepository {

    val allData: LiveData<List<Article>> = articleDao.readAllData()

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


    override suspend fun addArticleList(articleList: List<Article>) {
        return articleDao.addArticle(articleList)
    }

    override suspend fun deleteAllArticles() {
        return articleDao.deleteAll()
    }

}