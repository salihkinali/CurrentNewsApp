package com.salihkinali.currentnewsapp.data.repository

import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.data.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper,private val dao: ArticleDao) {

    //For Remote
    suspend fun getNews() = apiHelper.getNews()
    suspend fun getResult(query: String?) = apiHelper.getResult(query)

    //For Local Repo
    fun getList() = dao.getAllNews()
    suspend fun insert(article: Article) = dao.insert(article)
    suspend fun delete(article: String) = dao.delete(article)
    suspend fun newChecking(id: Int) = dao.newChecking(id)
    suspend fun deleteFromFavorite(articleRoom: Article) = dao.deleteFromFavorite(articleRoom)


}