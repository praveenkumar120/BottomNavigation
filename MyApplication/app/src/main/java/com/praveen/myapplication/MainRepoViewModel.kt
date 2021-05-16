package com.praveen.myapplication

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.praveen.myapplication.data.ItemsItem
import com.praveen.myapplication.service.ApiService

class MainRepoViewModel(private val api: ApiService) : ViewModel() {

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    private val _userList = MutableLiveData<PagingData<ItemsItem>>()
    val userList: LiveData<PagingData<ItemsItem>>
        get() = _userList


    val repoList = Pager(PagingConfig(pageSize = 10)) {
        PagingDataSource(api)
    }.flow.cachedIn(viewModelScope)
}