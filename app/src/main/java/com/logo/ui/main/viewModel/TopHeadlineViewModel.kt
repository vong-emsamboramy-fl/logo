package com.logo.ui.main.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.logo.data.model.headline.Article
import com.logo.data.model.headline.ArticleDatabase
import com.logo.data.model.headline.TopHeadline
import com.logo.data.model.search.SearchHistory
import com.logo.data.model.search.SearchHistoryDao
import com.logo.data.model.search.SearchHistoryDatabase
import com.logo.data.model.search.SearchModel
import com.logo.data.repository.HeadlineRepository
import com.logo.data.repository.SearchHistoryRepository
import com.logo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopHeadlineViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: HeadlineRepository
    private val searchRepo: SearchHistoryRepository

    private val postTopHeadlines: MutableLiveData<Resource<TopHeadline>> = MutableLiveData()
    val observeTopHeadlines: LiveData<Resource<TopHeadline>> = postTopHeadlines

    private val postSearch: MutableLiveData<Resource<TopHeadline>> = MutableLiveData()
    val observeSearch: LiveData<Resource<TopHeadline>> = postSearch

    val searchHistorys: LiveData<List<SearchHistory>>
    val articleList: LiveData<List<Article>>

    init {
        val searchDao = SearchHistoryDatabase.getDatabase(application).searchHistoryDao()
        searchRepo = SearchHistoryRepository(searchDao)
        searchHistorys = searchRepo.allData

        val articleDao = ArticleDatabase.getDatabase(application).articleDao()
        repo = HeadlineRepository(articleDao)
        articleList = repo.allData

    }


    fun getTopHeadlines() {
        postTopHeadlines.postValue(Resource.loading(null))
        repo.getTopHeadlines().enqueue(object : Callback<TopHeadline> {
            override fun onResponse(call: Call<TopHeadline>, response: Response<TopHeadline>) {
                response.body()?.let {
                    postTopHeadlines.postValue(Resource.success(it))
                }
            }

            override fun onFailure(call: Call<TopHeadline>, t: Throwable) {
                postTopHeadlines.postValue(Resource.error(t.message, null))
            }
        })
    }

    fun search(body: SearchModel) {
        postSearch.postValue(Resource.loading(null))
        repo.search(body).enqueue(object : Callback<TopHeadline> {
            override fun onResponse(call: Call<TopHeadline>, response: Response<TopHeadline>) {
                response.body()?.let {
                    postSearch.postValue(Resource.success(it))
                }
                postSearch.postValue(Resource.success(response.body()))
            }

            override fun onFailure(call: Call<TopHeadline>, t: Throwable) {
                postSearch.postValue(Resource.error(t.message, null))
            }
        })
    }

    fun addSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.addSearchHistory(searchHistory)
        }
    }

    fun addArticle(articleList: List<Article>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addArticleList(articleList)
        }
    }

    fun deleteAllArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllArticles()
        }
    }

}