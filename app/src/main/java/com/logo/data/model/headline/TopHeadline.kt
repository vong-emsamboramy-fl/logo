package com.logo.data.model.headline


data class TopHeadline(
    val totalArticles: Int,
    val articles: List<Article>
)

data class Article(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val source: ArticleSource
)

data class ArticleSource(
    val name: String,
    val url: String
)