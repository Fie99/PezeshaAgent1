package com.pezesha.agent.data.remote.interfaces

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    suspend fun login(@Body loginModel: String): Response<String>

}