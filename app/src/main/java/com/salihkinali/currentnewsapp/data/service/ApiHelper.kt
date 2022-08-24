package com.salihkinali.currentnewsapp.data.service

import retrofit2.http.Query

class ApiHelper(private val apiService: ApiService) {

    suspend fun getNews() = apiService.getNews()
    suspend fun getResult(query: String?) = apiService.getResult(query)
}