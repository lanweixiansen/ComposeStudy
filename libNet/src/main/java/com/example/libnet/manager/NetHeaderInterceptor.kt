package com.example.libnet.manager

import com.example.lib_base.BuildConfig
import com.example.lib_base.manager.AppData
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class NetHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val headers = Headers.headersOf(
            "token",
            AppData.getToken().toString(),
            "system_time",
            (System.currentTimeMillis() / 1000).toString(),
            "version_name",
            BuildConfig.VERSION_NAME,
            "version_code",
            BuildConfig.VERSION_CODE
        )
        return chain.proceed(builder.headers(headers).url(request.url).build())
    }
}
