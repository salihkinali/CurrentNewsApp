package com.salihkinali.currentnewsapp.ui.viewmodel


import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.model.News
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.data.service.ApiHelper
import com.salihkinali.currentnewsapp.data.service.RetrofitBuilder
import com.salihkinali.currentnewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsViewModel(): ViewModel() {

      private val mainRepository = MainRepository(ApiHelper(RetrofitBuilder.apiService))
        fun getUsers() = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = mainRepository.getNews()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
            }
        }
}