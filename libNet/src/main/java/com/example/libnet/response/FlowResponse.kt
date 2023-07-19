package com.example.libnet.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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

fun <T> requestLiveData(
    requestCall: suspend () -> BaseResponse<T>?,
    errorBlock: ((Int, String?) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
): LiveData<T?> {
    return liveData {
        var data: T? = null
        flowResponse(requestCall, errorBlock, onComplete).collect {
            data = it?.data
        }
        emit(data)
    }
}

suspend fun <T> requestFlow(
    requestCall: suspend () -> BaseResponse<T>?,
    errorBlock: ((Int, String?) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
): T? {
    var data: T? = null
    flowResponse(requestCall, errorBlock, onComplete).collect {
        data = it?.data
    }
    return data
}

suspend fun <T> flowResponse(
    requestCall: suspend () -> BaseResponse<T>?,
    errorBlock: ((Int, String?) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
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
    }.flowOn(Dispatchers.IO)
        .onStart {
//        if (showLoading) LoadingUtils.showLoading()
        }.catch { e ->
            e.printStackTrace()
            val handler = ErrorExceptionManager.handlerException(e)
            errorBlock?.invoke(handler.errCode, handler.errMsg)
        }.onCompletion {
//        if (showLoading) LoadingUtils.disLoading()
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