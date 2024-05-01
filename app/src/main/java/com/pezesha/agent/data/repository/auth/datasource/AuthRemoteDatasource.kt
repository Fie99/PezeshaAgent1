package com.pezesha.agent.data.repository.auth.datasource

import com.pezesha.agent.data.remote.utils.Resource

interface AuthRemoteDatasource {

    suspend fun login(model: String): Resource<String>

}