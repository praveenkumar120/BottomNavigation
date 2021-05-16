package com.praveen.myapplication.repository

import com.praveen.myapplication.service.ApiService

class MainRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun getMainRepository(page: Int) =
        safeApiCall { apiService.getRepoData(page) }
}