package com.logo.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logo.data.model.headline.TopHeadline
import com.logo.data.repository.HeadlineRepository
import com.logo.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopHeadlineViewModel : ViewModel() {

    private val repo = HeadlineRepository()

    private val postTopHeadlines: MutableLiveData<Resource<TopHeadline>> = MutableLiveData()
    val observeTopHeadlines: LiveData<Resource<TopHeadline>> = postTopHeadlines

    fun getTopHeadlines() {
        postTopHeadlines.postValue(Resource.loading(null))
        repo.getTopHeadlines().enqueue(object : Callback<TopHeadline> {
            override fun onResponse(call: Call<TopHeadline>, response: Response<TopHeadline>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        postTopHeadlines.postValue(Resource.success(it))
                    }
                } else {
                    postTopHeadlines.postValue(
                        Resource.error(
                            response.message(),
                            response.code(),
                            null
                        )
                    )
                }
            }

            override fun onFailure(call: Call<TopHeadline>, t: Throwable) {
                postTopHeadlines.postValue(Resource.error(t.message, 0, null))
            }
        })
    }

}