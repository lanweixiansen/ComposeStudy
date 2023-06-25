package com.example.libnet.manager

import android.util.Log
import com.blankj.utilcode.util.NetworkUtils
import com.example.libnet.BuildConfig
import com.example.libnet.exception.ERROR
import com.example.libnet.exception.NoNetWorkException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {
    private val mRetrofit: Retrofit
    val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(initOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun <T : Any> created(apiServer: Class<T>): T {
        return mRetrofit.create(apiServer)
    }

    private fun initOkHttpClient(): OkHttpClient {
        val build = OkHttpClient.Builder()
            .connectTimeout(12, TimeUnit.MINUTES)
            .writeTimeout(12, TimeUnit.MINUTES)
            .readTimeout(12, TimeUnit.MINUTES)
        //TODO: Header拦截器
//        build.addInterceptor()
//        build.addInterceptor()
        val httpLoggingInterceptor = HttpLoggingInterceptor {
            Log.i("okHttp", "msg: $it")
        }
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        build.addInterceptor(httpLoggingInterceptor)
        build.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                if (NetworkUtils.isConnected()) {
                    val request = chain.request()
                    return chain.proceed(request)
                } else {
                    throw NoNetWorkException(ERROR.NETWORK_ERROR)
                }
            }
        })
        return build.build()
    }
}