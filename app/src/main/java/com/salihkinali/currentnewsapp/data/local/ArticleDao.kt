package com.salihkinali.currentnewsapp.data.local

import androidx.room.*
import com.salihkinali.currentnewsapp.data.model.ArticleRoom

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: ArticleRoom)

    @Query("DELETE FROM news_table WHERE title = :news")
    suspend fun delete(news: String)

    @Query("SELECT * FROM news_table")
    suspend fun getAllNews(): List<ArticleRoom>

    @Query("SELECT * FROM news_table WHERE  description  = :content")
    suspend fun newChecking(content:String):List<ArticleRoom>?

}