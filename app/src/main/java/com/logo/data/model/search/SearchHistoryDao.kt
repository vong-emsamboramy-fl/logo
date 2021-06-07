package com.logo.data.model.search

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSearchHistory(searchHistory: SearchHistory)

    @Query("SELECT * FROM search_history ORDER BY id DESC")
    fun readAllData() : LiveData<List<SearchHistory>>

}