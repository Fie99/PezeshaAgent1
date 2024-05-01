package com.pezesha.agent.domain.auth.usecase

import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.auth.AuthRepository

class GetLoginUseCase(private val repository: AuthRepository) {

    suspend fun execute(model: String): Resource<String> {
        return repository.login(model)
    }

}