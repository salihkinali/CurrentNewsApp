package com.salihkinali.currentnewsapp.ui.fragment.search

import androidx.lifecycle.*
import com.salihkinali.currentnewsapp.data.model.News
import com.salihkinali.currentnewsapp.data.repository.MainRepository
import com.salihkinali.currentnewsapp.util.Status
import com.salihkinali.currentnewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private var _status = MutableLiveData<Resource<News>>()
    val status: LiveData<Resource<News>> get() = _status

    fun searchQuery(query: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(Resource(Status.LOADING,data = null,message = null))
            delay(1000)
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
