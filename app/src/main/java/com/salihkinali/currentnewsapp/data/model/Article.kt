package com.salihkinali.currentnewsapp.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "news_table")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String?,
    val description: String?,
    val image: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?
):Parcelable