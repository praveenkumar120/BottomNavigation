package com.praveen.myapplication.result

import androidx.lifecycle.MutableLiveData

sealed class Results<out T> {
    data class Success<out T>(val value: T) : Results<T>()
    data class Failure(val statusCode: Int, val errorStatus: ErrorStatus) : Results<Nothing>()
    object Loading : Results<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$value]"
            is Failure -> "Error[statusCode=$statusCode, exception=$errorStatus]"
            Loading -> "Loading"
        }
    }
}

enum class ErrorStatus {
    HTTP_EXCEPTION,
    NO_CONNECTION,
    UNKNOWN,
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Results<*>.succeeded
    get() = this is Results.Success && value != null

fun <T> Results<T>.successOr(fallback: T): T {
    return (this as? Results.Success<T>)?.value ?: fallback
}

val <T> Results<T>.value: T?
    get() = (this as? Results.Success)?.value

/**
 * Updates value of [liveData] if [Result] is of type [Success]
 */
inline fun <reified T> Results<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is Results.Success) {
        liveData.value = value
    }
}
