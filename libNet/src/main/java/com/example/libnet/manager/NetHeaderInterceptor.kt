package com.example.libnet.manager

import com.example.lib_base.manager.AppData
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class NetHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val headerMap = mutableMapOf<String, String?>()
        headerMap["token"] = AppData.getToken()
        headerMap["system_time"] = (System.currentTimeMillis() / 1000).toString()
        return chain.proceed(builder.headers(Headers.of(headerMap)).url(request.url()).build())
    }
}