package com.example.libnet.result

import com.example.libnet.exception.ApiException

sealed class NetWorkResult<out T> {
    data class Success<out T>(val data: T) : NetWorkResult<T>()

    data class Error(val exception: ApiException) : NetWorkResult<Nothing>()

    data class Loading(val loading: Boolean) : NetWorkResult<Nothing>()

    data class Complete(val complete: Boolean) : NetWorkResult<Nothing>()
}