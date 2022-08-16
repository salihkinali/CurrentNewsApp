package com.salihkinali.currentnewsapp.data.model


import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("totalArticles")
    val totalArticles: Int
)