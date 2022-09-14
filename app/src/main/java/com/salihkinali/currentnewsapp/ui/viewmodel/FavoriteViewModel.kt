package com.salihkinali.currentnewsapp.ui.viewmodel

import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.local.ArticleDao
import com.salihkinali.currentnewsapp.data.model.ArticleRoom
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteViewModel(private val dao: ArticleDao) : ViewModel() {

    private val _newList = MutableLiveData<Resource<List<ArticleRoom>>>()
    val newList: LiveData<Resource<List<ArticleRoom>>> = _newList

    init {
        getList()
    }

    private fun getList() {
        _newList.postValue(Resource(status = Status.LOADING, data = null, message = null))
        viewModelScope.launch {
            try {
                val result = dao.getAllNews()
                _newList.postValue(Resource(status = Status.SUCCESS, data = result, message = null))
            } catch (e: Exception) {
                _newList.postValue(
                    Resource(
                        status = Status.SUCCESS,
                        message = e.toString(),
                        data = null
                    )
                )
            }
        }
    }

    fun insertNew(articleRoom: ArticleRoom) {
        viewModelScope.launch {
            dao.insert(articleRoom)
            getList()
        }
    }

    fun deleteNew(article:String) {
        viewModelScope.launch {
            dao.delete(article)
        }
    }

    fun isFavorite(content:String): Boolean {
        var favoriteNew = false
        viewModelScope.launch {
           val checkedNew = dao.newChecking(content)
            favoriteNew = checkedNew.isNotEmpty()
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
