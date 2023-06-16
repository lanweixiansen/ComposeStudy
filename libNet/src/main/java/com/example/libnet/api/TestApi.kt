package com.example.libnet.api

import com.example.libnet.response.BaseResponse
import retrofit2.http.GET

interface TestApi {

    @GET("")
    fun test(): BaseResponse<String>

}