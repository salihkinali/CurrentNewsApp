package com.salihkinali.currentnewsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val dao: ArticleDao) : ViewModel() {

    fun getList() = dao.getAllNews()

    fun insertNew(article: Article) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                dao.insert(article)
            } catch (error: Exception) {
               Log.e("About Data : ",error.toString())
            }

        }
    }

    fun deleteNew(article:String) {
        viewModelScope.launch {
            try {
                dao.delete(article)
            } catch (error: Exception) {
                Log.e("About Data : ",error.toString())
            }
        }


    }

    fun isFavorite(title:String): Boolean {
        var favoriteNew = false
        viewModelScope.launch {
            val checkedNew = dao.newChecking(title)
            if (checkedNew != null) {
                favoriteNew = checkedNew.isNotEmpty()
            }
        }
        return favoriteNew
    }

    fun deleteFromFavorite(articleRoom: Article) {
        viewModelScope.launch {
            dao.deleteFromFavorite(articleRoom)
            getList()
        }
    }

}

class NewViewModelFactory(private val dao: ArticleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
