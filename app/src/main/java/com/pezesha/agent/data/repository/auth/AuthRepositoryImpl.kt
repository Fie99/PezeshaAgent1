package com.pezesha.agent.data.repository.auth

import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.data.repository.auth.datasource.AuthRemoteDatasource
import com.pezesha.agent.domain.auth.AuthRepository

class AuthRepositoryImpl(private val authRemoteDatasource: AuthRemoteDatasource) : AuthRepository {

    override suspend fun login(loginModel: String): Resource<String> {
        return authRemoteDatasource.login(loginModel)
    }


}