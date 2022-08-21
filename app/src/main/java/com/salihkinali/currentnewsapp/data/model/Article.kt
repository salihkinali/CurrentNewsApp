package com.salihkinali.currentnewsapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
):Serializable