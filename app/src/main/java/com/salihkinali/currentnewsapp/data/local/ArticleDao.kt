package com.salihkinali.currentnewsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.salihkinali.currentnewsapp.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: Article)

    @Query("DELETE FROM news_table WHERE title = :news")
    suspend fun delete(news: String)

    @Query("SELECT * FROM news_table")
    fun getAllNews(): LiveData<List<Article>>

    @Query("SELECT * FROM news_table WHERE  description  = :content")
    suspend fun newChecking(content:String):List<Article>?

    @Delete
    suspend fun deleteFromFavorite(articleRoom: Article)


}