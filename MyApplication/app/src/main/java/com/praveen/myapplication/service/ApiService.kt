package com.praveen.myapplication.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.praveen.myapplication.data.DetailResponse
import com.praveen.myapplication.data.MainRepoResponse
import com.praveen.myapplication.service.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("search/users?q=caged")
    suspend fun getRepoData(
        @Query("page") page: Int,
        @Query("per_page") limit: Int = 5
    ): MainRepoResponse?


    @GET("repos/mojombo/30daysoflaptops.github.io")
    suspend fun getDetailRepoData(
    ): DetailResponse?


    companion object {
        fun create(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClientBuilder)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
        }
    }
}