package com.example.libnet.result

import com.example.libnet.exception.ApiException

sealed class NetWorkResult<out T> {
    data class Success<out T>(val data: T) : NetWorkResult<T>()

    data class Error(val exception: ApiException) : NetWorkResult<Nothing>()

    object Loading : NetWorkResult<Nothing>()

    data class Complete(val complete: Boolean) : NetWorkResult<Nothing>()
}