package com.logo.data.repository

import androidx.lifecycle.LiveData
import com.logo.data.model.search.SearchHistory
import com.logo.data.model.search.SearchHistoryDao

interface ISearchHistoryRepository {
    suspend fun addSearchHistory(searchHistory: SearchHistory)
}

class SearchHistoryRepository(private val searchDao: SearchHistoryDao) : ISearchHistoryRepository {

    val allData: LiveData<List<SearchHistory>> = searchDao.readAllData()

    override suspend fun addSearchHistory(searchHistory: SearchHistory) {
        return searchDao.addSearchHistory(searchHistory)
    }

}