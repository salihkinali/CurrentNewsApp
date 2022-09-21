package com.salihkinali.currentnewsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.salihkinali.currentnewsapp.data.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}