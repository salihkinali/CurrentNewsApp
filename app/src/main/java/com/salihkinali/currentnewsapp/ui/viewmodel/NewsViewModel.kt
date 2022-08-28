package com.salihkinali.currentnewsapp.ui.viewmodel


import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.model.News
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.data.service.ApiHelper
import com.salihkinali.currentnewsapp.data.service.RetrofitBuilder
import com.salihkinali.currentnewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsViewModel() : ViewModel() {

    private val mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService))
    private var _responseNews = MutableLiveData<Resource<News>>()
    val responseNews: LiveData<Resource<News>> get() = _responseNews

    init {
        getNews()
    }

    private fun getNews() {
        _responseNews.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = mainRepository.getNews()
                _responseNews.postValue(Resource.success(data = result))
            }catch (e:Exception){
                _responseNews.postValue(Resource.error(data = null, message = e.toString()))
            }
        }
    }

//    fun getUsers() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = mainRepository.getNews()))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
//        }
//    }
}