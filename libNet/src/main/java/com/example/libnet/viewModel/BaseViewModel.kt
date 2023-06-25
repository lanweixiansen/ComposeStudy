package com.example.libnet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_base.ext.saveAs
import com.example.libnet.exception.ApiException
import com.example.libnet.manager.HttpManager
import com.example.libnet.response.BaseResponse
import com.example.libnet.response.requestFlow
import com.example.libnet.result.NetWorkResult
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun <T : Any> Class<T>.createdApi() = HttpManager.created(this)
open class BaseViewModel : ViewModel() {

    fun <T : Any?> netRequest(
        showLoading: Boolean = false,
        action: suspend () -> BaseResponse<T>?,
        result: (NetWorkResult<T>.() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            val date = requestFlow(
                requestCall = {
                    action()
                },
                onComplete = {
                    result?.invoke(NetWorkResult.Complete(true))
                },
                errorBlock = { errorCode, errorMsg ->
                    result?.invoke(NetWorkResult.Error(ApiException(errorCode, errorMsg)))
                }, showLoading = showLoading
            )
            date?.let { result?.invoke(NetWorkResult.Success(it)) }
        }
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