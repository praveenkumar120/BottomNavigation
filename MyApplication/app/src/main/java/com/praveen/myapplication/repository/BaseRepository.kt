package com.praveen.myapplication.repository

import android.accounts.NetworkErrorException
import com.praveen.myapplication.result.ErrorStatus
import com.praveen.myapplication.result.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Results<T> {
        return withContext(Dispatchers.IO) {
            try {
                Results.Success(apiCall.invoke())
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        Results.Failure(e.code(), ErrorStatus.HTTP_EXCEPTION)
                    }
                    is NetworkErrorException -> {
                        Results.Failure(0, ErrorStatus.NO_CONNECTION)
                    }
                    is UnknownHostException -> {
                        Results.Failure(0, ErrorStatus.NO_CONNECTION)
                    }
                    else -> {
                        Results.Failure(0, ErrorStatus.UNKNOWN)
                    }
                }
            }
        }
    }
}
