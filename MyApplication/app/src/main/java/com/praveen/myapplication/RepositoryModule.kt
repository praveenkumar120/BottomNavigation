package com.praveen.myapplication

import com.praveen.myapplication.repository.MainRepository
import com.praveen.myapplication.service.ApiService

fun provideMainRepository(apiService: ApiService): PagingDataSource {
    return PagingDataSource(apiService)
}

fun provideDetailedRepository(apiService: ApiService): MainRepository {
    return MainRepository(apiService)
}
