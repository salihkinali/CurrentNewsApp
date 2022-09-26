package com.salihkinali.currentnewsapp.data.service

import com.salihkinali.currentnewsapp.data.model.News
import com.salihkinali.currentnewsapp.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/v4/top-headlines")
    suspend fun getNews(
        @Query("token") token:String = API_KEY,
        @Query("lang")  lang: String = "en"
    ):News

    @GET("api/v4/search")
    suspend fun getResult(
        @Query("q") query:String? = null,
        @Query("token") token:String = API_KEY,
    ): News

}