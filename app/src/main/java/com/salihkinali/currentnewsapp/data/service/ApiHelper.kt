package com.salihkinali.currentnewsapp.data.service

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    suspend fun getNews(topic:String) = apiService.getNews(topic = topic)
    suspend fun getResult(query: String?) = apiService.getResult(query)
}