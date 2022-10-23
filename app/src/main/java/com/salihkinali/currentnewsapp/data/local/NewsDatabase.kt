package com.salihkinali.currentnewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.salihkinali.currentnewsapp.data.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}