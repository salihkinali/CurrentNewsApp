package com.salihkinali.currentnewsapp.data.service

class ApiHelper(private val apiService: ApiService) {

    suspend fun getNews() = apiService.getNews()
}