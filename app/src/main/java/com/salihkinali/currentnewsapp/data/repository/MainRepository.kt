package com.salihkinali.currentnewsapp.data.repository

import com.salihkinali.currentnewsapp.data.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getNews() = apiHelper.getNews()
}