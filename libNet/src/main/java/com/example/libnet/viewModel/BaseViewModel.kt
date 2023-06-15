package com.example.libnet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_base.ext.saveAs
import com.example.libnet.api.TestApi
import com.example.libnet.exception.ApiException
import com.example.libnet.manager.HttpManager
import com.example.libnet.response.BaseResponse
import com.example.libnet.response.requestFlow
import com.example.libnet.result.NetWorkResult
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

open class BaseViewModel : ViewModel() {

    val api by lazy { HttpManager.created(TestApi::class.java) }

    fun test() {
        viewModelScope.launch {
            viewModelRequest(action = { api.test() }).onComplete {

            }.onSuccess {

            }.onError {
                it.errCode
            }
        }
    }

    fun <T : Any?> request(
        showLoading: Boolean = false,
        action: suspend () -> BaseResponse<T>?,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((ApiException) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            viewModelRequest(showLoading = showLoading, action = { action() }).onError {
                onError?.invoke(it)
            }.onComplete {
                onComplete?.invoke()
            }.onSuccess {
                onSuccess?.invoke(it)
            }
        }
    }

    private suspend fun <T : Any?> viewModelRequest(
        showLoading: Boolean = false,
        action: suspend () -> BaseResponse<T>?,
    ): NetWorkResult<T> {
        var result: NetWorkResult<T> = NetWorkResult.Loading(showLoading)
        val date = requestFlow(
            showLoading = {

            },
            requestCall = {
                action()
            },
            onComplete = {
                result = NetWorkResult.Complete(true)
            },
            errorBlock = { errorCode, errorMsg ->
                result = NetWorkResult.Error(ApiException(errorCode, errorMsg))
            })
        date?.let { result = NetWorkResult.Success(it) }
        return result
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T> NetWorkResult<T>.onSuccess(action: (T) -> Unit): NetWorkResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is NetWorkResult.Success) {
        action.invoke(this.saveAs<NetWorkResult.Success<T>>().data)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> NetWorkResult<T>.onError(action: (e: ApiException) -> Unit): NetWorkResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is NetWorkResult.Error) {
        action.invoke(this.saveAs<NetWorkResult.Error>().exception)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> NetWorkResult<T>.onComplete(action: () -> Unit): NetWorkResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is NetWorkResult.Complete) {
        action.invoke()
    }
    return this
}