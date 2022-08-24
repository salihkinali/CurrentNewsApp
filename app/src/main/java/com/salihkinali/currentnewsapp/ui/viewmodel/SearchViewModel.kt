package com.salihkinali.currentnewsapp.ui.viewmodel

import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.model.News
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.data.service.ApiHelper
import com.salihkinali.currentnewsapp.data.service.RetrofitBuilder
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

    private val mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService))
    private var _status = MutableLiveData<Resource<News>>()
    val status: LiveData<Resource<News>> get() = _status

    fun searchQuery(query: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            Resource.loading(data = null)
            try {
                query?.let {
                    _status.postValue(Resource(status = Status.SUCCESS, data = mainRepository.getResult(it), message = null))
                }
            } catch (e: Exception) {
                _status.postValue(Resource(status = Status.ERROR, data = null, message = e.toString()))
            }
        }
    }

}