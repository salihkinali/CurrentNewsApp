package com.salihkinali.currentnewsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.model.Article
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getList() = mainRepository.getList()

    fun insertNew(article: Article) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                mainRepository.insert(article)
            } catch (error: Exception) {
               Log.e("About Data : ",error.toString())
            }

        }
    }

    fun deleteNew(article:String) {
        viewModelScope.launch {
            try {
               mainRepository.delete(article)
            } catch (error: Exception) {
                Log.e("About Data : ",error.toString())
            }
        }


    }

    fun isFavorite(title:String): Boolean {
        var favoriteNew = false
        viewModelScope.launch {
            val checkedNew = mainRepository.newChecking(title)
            if (checkedNew != null) {
                favoriteNew = checkedNew.isNotEmpty()
            }
        }
        return favoriteNew
    }

    fun deleteFromFavorite(articleRoom: Article) {
        viewModelScope.launch {
           mainRepository.deleteFromFavorite(articleRoom)
            getList()
        }
    }

}

/*class FavoriteViewModelFactory(private val dao: ArticleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
