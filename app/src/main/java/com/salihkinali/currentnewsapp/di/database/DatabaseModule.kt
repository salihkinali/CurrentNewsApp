package com.salihkinali.currentnewsapp.di.database

import android.content.Context
import androidx.room.Room
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun getLocalDatabase(@ApplicationContext context: Context): NewsDatabase =
      Room.databaseBuilder(context, NewsDatabase::class.java,"new_database")
          .build()

    @Provides
    @Singleton
    fun getArticleDao(newDatabese: NewsDatabase): ArticleDao = newDatabese.articleDao()
}