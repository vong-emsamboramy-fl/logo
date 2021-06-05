package com.logo.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logo.data.model.headline.TopHeadline
import com.logo.data.model.search.SearchModel
import com.logo.data.repository.HeadlineRepository
import com.logo.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopHeadlineViewModel : ViewModel() {

    private val repo = HeadlineRepository()

    private val postTopHeadlines: MutableLiveData<Resource<TopHeadline>> = MutableLiveData()
    val observeTopHeadlines: LiveData<Resource<TopHeadline>> = postTopHeadlines

    private val postSearch: MutableLiveData<Resource<TopHeadline>> = MutableLiveData()
    val observeSearch: LiveData<Resource<TopHeadline>> = postSearch

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

}