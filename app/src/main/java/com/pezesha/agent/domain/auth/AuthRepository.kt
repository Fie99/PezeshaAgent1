package com.pezesha.agent.domain.auth

import com.pezesha.agent.data.remote.utils.Resource

interface AuthRepository {

    suspend fun login(loginModel: String): Resource<String>

}