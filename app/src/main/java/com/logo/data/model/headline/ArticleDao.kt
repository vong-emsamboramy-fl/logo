package com.logo.data.model.headline

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArticle(articleList: List<Article>)

    @Query("SELECT * FROM article ORDER BY id DESC")
    fun readAllData(): LiveData<List<Article>>


    @Query("DELETE FROM article")
    fun deleteAll()

}