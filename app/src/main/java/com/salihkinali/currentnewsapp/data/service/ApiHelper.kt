package com.salihkinali.currentnewsapp.data.service

class ApiHelper(private val apiService: ApiService) {

    suspend fun getNews(topic:String) = apiService.getNews(topic = topic)
    suspend fun getResult(query: String?) = apiService.getResult(query)
}