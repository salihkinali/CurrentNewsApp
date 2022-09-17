package com.salihkinali.currentnewsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.model.ArticleRoom
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val dao: ArticleDao) : ViewModel() {

    fun getList() = liveData(Dispatchers.Main) {
        emit(Resource(Status.LOADING,data = null, message = null))
        try {
            val result = dao.getAllNews()
            emit(Resource(Status.SUCCESS,data = result, message = null))
        }catch (e: Exception){
            emit(Resource(Status.ERROR,data = null, message = e.toString()))
        }
    }


    fun insertNew(articleRoom: ArticleRoom) {
        viewModelScope.launch {
            try {
                dao.insert(articleRoom)


            } catch (error: Exception) {
                Log.e("Waiting Error Situation => ",error.toString())
            }

        }
    }

    fun deleteNew(article:String) {
        viewModelScope.launch {
            dao.delete(article)
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
