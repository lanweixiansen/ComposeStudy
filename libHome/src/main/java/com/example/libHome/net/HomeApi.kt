package com.example.libHome.net

import com.example.libHome.data.Banner
import com.example.libnet.response.BaseResponse
import retrofit2.http.GET

interface HomeApi {
    /**
     * 首页轮播图
     */
    @GET("/banner/json")
    suspend fun getHomeBanner(): BaseResponse<MutableList<Banner>?>
}