package com.praveen.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.myapplication.data.MainRepoResponse
import com.praveen.myapplication.repository.MainRepository
import com.praveen.myapplication.result.Results
import kotlinx.coroutines.launch

class DetailedRepoViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _detailList = MutableLiveData<MainRepoResponse>()
    val detailList: LiveData<MainRepoResponse>
        get() = _detailList

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    fun setDetailData() {
        viewModelScope.launch {
            _loader.value = true
            when (val result = mainRepository.getMainRepository(1)) {
                is Results.Success -> {
                    _detailList.value = result.value
                    _loader.value = false
                }
                is Results.Failure -> {
                    _loader.value = false
                }
            }
        }
    }
}