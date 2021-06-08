package com.logo.data.model.headline

import androidx.room.Entity
import androidx.room.PrimaryKey


data class TopHeadline(
    val totalArticles: Int,
    val articles: List<Article>
)

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String
)