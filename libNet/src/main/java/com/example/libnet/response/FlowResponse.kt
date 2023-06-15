package com.example.libnet.response

import com.example.libnet.exception.ApiException
import com.example.libnet.exception.ErrorExceptionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withTimeout


suspend fun <T> requestFlow(
    showLoading: ((Boolean) -> Unit)? = null,
    requestCall: suspend () -> BaseResponse<T>?,
    errorBlock: ((Int, String?) -> Unit)? = null,
    onComplete: (() -> Unit)? = null
): T? {
    var data: T? = null
    flowResponse(showLoading, requestCall, errorBlock, onComplete).collect {
        data = it?.data
    }
    return data
}

suspend fun <T> flowResponse(
    showLoading: ((Boolean) -> Unit)? = null,
    requestCall: suspend () -> BaseResponse<T>?,
    errorBlock: ((Int, String?) -> Unit)? = null,
    onComplete: (() -> Unit)? = null
): Flow<BaseResponse<T>?> {
    return flow {
        //设置超时时间
        val response = withTimeout(10 * 1000) {
            requestCall()
        }
        if (response?.callFiled() == true) {
            throw ApiException(response.errorCode, response.errorMsg)
        }
        emit(response)
    }.flowOn(Dispatchers.IO).onStart {
        showLoading?.invoke(true)
    }.catch { e ->
        e.printStackTrace()
        val handler = ErrorExceptionManager.handlerException(e)
        errorBlock?.invoke(handler.errCode, handler.errMsg)
    }.onCompletion {
        showLoading?.invoke(false)
        onComplete?.invoke()
    }
}

data class BaseResponse<out T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String?
) {
    fun callFiled() = errorCode != 0
}