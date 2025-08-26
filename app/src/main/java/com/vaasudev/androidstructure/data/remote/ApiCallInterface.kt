package com.vaasudev.androidstructure.data.remote

import com.vaasudev.androidstructure.domain.response.InitResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiCallInterface {

    @GET()
    suspend fun callInit(
        @Url url: String
    ): Response<InitResponse>
}