package com.pezesha.agent.data.repository.auth.datasource

import com.pezesha.agent.data.remote.interfaces.AuthApi
import com.pezesha.agent.data.remote.utils.Resource

class AuthRemoteDatasourceImpl(private val api: AuthApi) : AuthRemoteDatasource {

    override suspend fun login(model: String): Resource<String> {
        return Resource.Success(model)
//        val response = api.login(model)
//        return if (response.isSuccessful) Resource.Success(response.body()!!)
//        else {
//            var errorResponse: LoginResponse? = null
//            response.errorBody()?.let {
//                errorResponse = gson.fromJson(it.string(), LoginResponse::class.java)
//            }
//            Resource.Error(response.message(), errorResponse)
//        }
    }
}