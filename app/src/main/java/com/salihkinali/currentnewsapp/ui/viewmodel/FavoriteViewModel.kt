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

    fun isFavorite(id:Int): List<Article>? {
        var temp:List<Article>? = null
        viewModelScope.launch {
            temp = mainRepository.newChecking(id)

        }
        return temp
    }

    fun deleteFromFavorite(articleRoom: Article) {
        viewModelScope.launch {
           mainRepository.deleteFromFavorite(articleRoom)
            getList()
        }
    }

}

