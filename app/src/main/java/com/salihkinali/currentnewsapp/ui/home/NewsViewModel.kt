package com.salihkinali.currentnewsapp.ui.home


import android.util.Log
import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.model.News
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.util.BREAKING_NEWS
import com.salihkinali.currentnewsapp.util.TECHNOLOGY_NEWS
import com.salihkinali.currentnewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

   // private val mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService))
    private var _responseNews = MutableLiveData<Resource<MutableList<News>>>()
    val responseNews: LiveData<Resource<MutableList<News>>> get() = _responseNews

    init {
        getNews()
        Log.e("İnit Blok","Init bloğu bir kez çalıştı.")
    }

    private fun getNews() {
        _responseNews.postValue(Resource.loading(data = null))
        val results = mutableListOf <News>()
     viewModelScope.launch {
            try {
                val breaking = mainRepository.getNews(topic = BREAKING_NEWS)
                delay(1000)
                val technology = mainRepository.getNews(topic = TECHNOLOGY_NEWS)
                results.add(breaking)
                results.add(technology)
                _responseNews.postValue(Resource.success(data = results))
            }catch (e:Exception){
                _responseNews.postValue(Resource.error(data = null, message = e.toString()))
            }
        }
    }
}

