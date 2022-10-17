package com.salihkinali.currentnewsapp.ui.fragment.home


import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.model.News
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.util.BREAKING_NEWS
import com.salihkinali.currentnewsapp.util.TECHNOLOGY_NEWS
import com.salihkinali.currentnewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsViewModel(private val mainRepository: MainRepository) : ViewModel() {

   // private val mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService))
    private var _responseNews = MutableLiveData<Resource<MutableList<News>>>()
    val responseNews: LiveData<Resource<MutableList<News>>> get() = _responseNews

    init {
        getNews()
    }

    private fun getNews() {
        _responseNews.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val results = mutableListOf <News>()
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

