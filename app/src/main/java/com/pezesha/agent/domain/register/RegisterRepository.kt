package com.pezesha.agent.domain.register

import com.pezesha.agent.data.remote.utils.Resource

interface RegisterRepository {

    suspend fun sendConsent(phone: String): Resource<String>

    suspend fun verifyConsent(phone: String): Resource<String>

}