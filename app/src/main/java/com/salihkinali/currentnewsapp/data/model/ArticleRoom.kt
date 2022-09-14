package com.salihkinali.currentnewsapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "news_table")
data class ArticleRoom(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val content: String,
    val description: String,
    val image: String,
    val publishedAt: String,
   val title: String,
    val url: String
)