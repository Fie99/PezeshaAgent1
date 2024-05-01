package com.pezesha.agent.domain.register.useCase

import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.register.RegisterRepository

class GetRequestConsentUseCase(private val repository: RegisterRepository) {

    suspend fun execute(phone: String): Resource<String>{
        return repository.sendConsent(phone)
    }

}